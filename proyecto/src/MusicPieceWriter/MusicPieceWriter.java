package MusicPieceWriter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import MusicPiece.*;

//Conversión a Lilypond
//Generacion greedy ritmo
//Generación greedy melodía
//Etiquetas (tempo, compás, modalidad, tonalidad, registro)
//Armonía

public class MusicPieceWriter {
	int MAXSIZEQUEUE=500000;

	int compas;
	double K;
	List<MusicElement> mp, s;
	int numItems;
	//Queue<Node> q;
	Node best;


	PQsort pqs = new PQsort(); 
	PriorityQueue<Node> pq2 = new PriorityQueue<Node>(10, pqs); 

	static class PQsort implements Comparator<Node> { 
		public int compare(Node one, Node two) {
			return Double.compare(two.heuristica, one.heuristica); 
		}
	}


	class Node {
		int level, currentsize;
		double heuristica;
		List<MusicElement> musicElementList;
		Map<Integer, Boolean> pauses;
		double duration;

		protected Node( ) {
			level = -1;
			duration = 0;
			currentsize = 0;
			musicElementList = new ArrayList<MusicElement>();
			pauses = new HashMap<Integer,Boolean>();
		}


		public Node(Node original) {
			super();
			this.level = original.level;
			this.duration = original.duration;
			this.currentsize = original.currentsize;

			this.heuristica = original.heuristica;
			this.musicElementList = new ArrayList<MusicElement>();
			for(MusicElement ele : original.musicElementList)
				if(ele instanceof SyllableM)
					musicElementList.add(new SyllableM((SyllableM)ele));
				else
					musicElementList.add(new Pause((Pause)ele));
			this.pauses = new HashMap<Integer,Boolean>(original.pauses);

		}

		protected void add(double dur, MusicElement me, boolean extraPause) {
			if(me instanceof Pause)
				pauses.put(currentsize, extraPause);
			me.setDuration(dur);
			duration += dur;
			currentsize++;
			musicElementList.add(me);
		}


	}

	public MusicPieceWriter(MusicPiece m, int comp) {
		mp = m.getElements();
		compas = comp;
		Node root = new Node( );
		root.heuristica = 0;
		pq2.offer(root);
		best = null;
	}

	private void debug(Node nodo){
		System.err.println("HEU "+nodo.heuristica);
		System.err.println("SIZE ME "+nodo.musicElementList.size());
		for(MusicElement ele : nodo.musicElementList)
			ele.depurar();
		System.err.println("\nSize map "+nodo.pauses.size());

		for (Map.Entry<Integer, Boolean> entry : nodo.pauses.entrySet()) {
			System.err.print("Key = " + entry.getKey() + ", Value = " + entry.getValue()+"|||");
		}
		System.err.println();
	}

	private double calcularHeuristica(Node nodo, double importanciaProfundidad, double ajusteAlCompas, double relacionTonicasAtonas, double importanciaMediaVarianza, double mediaIdeal, double varianzaIdeal, double importanciaespacios){
		double heuristica = 0, tiempos = 0;
		int ntonicas = 0, bientonicas = 0;

		System.err.println(" Nombre\t\t\t\tparam\tvalor");
		//AJUSTE AL COMPÁS	 //calcular que estén bien colocados (sólo tónicas, número de tónicas y número de tónicas en tonos fuertes)
		for(MusicElement ele : nodo.musicElementList){
			if(ele instanceof SyllableM && ((SyllableM) ele).getTonic()){
				ntonicas++;
				if(tiempos != (int)tiempos || tiempos % compas != 0)
					bientonicas++;
			}
			tiempos += ele.getDuration();
		}
		if(ntonicas>0){
			heuristica = ajusteAlCompas * bientonicas / ntonicas;
			System.err.println("H CompasComponent\t"+ajusteAlCompas+" \t "+1.0*bientonicas / ntonicas+" = "+ajusteAlCompas * bientonicas / ntonicas);
		}
		//RELACION TÓNICAS VS ÁTONAS
		heuristica += relacionTonicasAtonas * relaciontonicasatonas(nodo);
		System.err.println("H TonAtonRelation\t"+relacionTonicasAtonas +" \t "+relaciontonicasatonas(nodo)+" = "+relacionTonicasAtonas * relaciontonicasatonas(nodo));

		//COMPONENTE PROFUNDIDAD
		heuristica += importanciaProfundidad*(nodo.level+1)/mp.size();
		System.err.println("H DeepComponent\t\t"+importanciaProfundidad +" \t "+(nodo.level+1.0)/mp.size()+" = "+importanciaProfundidad*(nodo.level+1)/mp.size());

		//COMPONENTE MEDIA
		double totalDur=0;
		int n =0;
		for(MusicElement nota : nodo.musicElementList)
			if(nota instanceof SyllableM){
				totalDur += nota.getDuration();
				n++;
			}
		double media = totalDur/n, varianza = 0;
		double mediacomp = (1 - Math.abs(mediaIdeal - media)/Math.max(media, mediaIdeal));
		heuristica += importanciaMediaVarianza * mediacomp;
		//COMPONENTE VARIANZA
		for(MusicElement nota : nodo.musicElementList)
			if(nota instanceof SyllableM)
				varianza += (nota.getDuration()-media) * (nota.getDuration()-media);
		varianza /= n;
		double varianzacomp = (1 - Math.abs(varianzaIdeal - varianza)/Math.max(varianzaIdeal, varianza));
		heuristica += importanciaMediaVarianza * varianzacomp;

		double debugAVGVARcomp = importanciaMediaVarianza * varianzacomp + importanciaMediaVarianza * mediacomp;
		//			System.err.println("H AVGVarComponent media-ideal-mediacomp "+media+"-"+mediaIdeal+" "+mediacomp+" var-ideal-varcomp "+varianza+"-"+varianzaIdeal+" "+varianzacomp+" form = "+debugAVGVARcomp);
		System.err.println("H AVGVarComponent\t"+importanciaMediaVarianza+" \t "+debugAVGVARcomp+" mediacomp: "+mediacomp+" varianzacomp: "+varianzacomp);

		//COMPONENTE ESPACIADO, contamos como buenas las pausas entre palabras que tienen de duración 0 y las pausas entre frases con duración mayor que 0
		int optionalPauses = 0, obligatoryPauses = 0, npausas = 0;

		for(MusicElement ele : nodo.musicElementList)
			if(ele instanceof Pause){
				npausas++;
				Pause pausa = (Pause) ele;
				if(pausa.getEnding() == ' '){
					if(pausa.getDuration() == 0)
						optionalPauses++;
				}
				else if(pausa.getDuration() > 0)
					obligatoryPauses++;
			}
		if(npausas>0){
			heuristica += importanciaespacios * (obligatoryPauses+optionalPauses)/npausas;
			System.err.println("H SpaceComponent\t"+importanciaespacios * (obligatoryPauses+optionalPauses)/npausas);
		}
		System.err.println("HEURISTICA "+heuristica);
		return heuristica;
	}

	public void vercola(){
		System.err.println("Estado cola TAM:"+pq2.size());
		System.err.println("---------------------------");
		for (Node e : pq2) {
			debug(e);
			System.err.println("-------");
		}
		System.err.println("---------------------------");
	}

	public void generateRhythm(SyllableM s1, SyllableM s2, double importanciaProfundidad, double ajusteAlCompas, double relacionTonicasAtonas, double importanciaMediaVarianza, double mediaIdeal, double varianzaIdeal, double importanciaespacios){
		System.err.println("tam lista "+mp.size());
		//regenerar ritmo?
/*		if(s!=null&&s.get(0).equals(s1) && s.get(s.size()-1).equals(s2)){//si se regenera la duración total es la misma o la misma más un múltiplo del compás para no descuadrar las notas siguientes
			System.err.println("regenerar");
//			totalDur=best.value;
		}*/
		int index=0, ini;
		numItems = mp.size();
		while(index<numItems&&!mp.get(index).equals(s1))
			index++;
		ini=index;
		index++;
		while(index<numItems&&!mp.get(index).equals(s2))
			index++;
		if(index == numItems){
			System.err.println("No se puede generar ritmo: Sílaba no encontrada");
			System.exit(1);
		}
		s=mp.subList(ini, index+1);
		numItems = s.size();
		boolean salir;
		salir=false;
		Node parent;
		while ( !pq2.isEmpty( ) && !salir) {
			vercola();
			parent = (Node)pq2.poll( );
			System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.err.println("PADRE level "+parent.level+" numitems "+numItems);
			debug(parent);
			System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			if (parent.level<numItems-1) {
				System.err.println("NO PAUSAS");
				for(double j=0.125; j<=4 ;j*=2){//for(int j=32;j>0;j/=2){
					System.err.println("Tiempo a asignar"+j);
					Node child = new Node(parent);
					child.level = parent.level+1;
					child.add(j, mp.get(child.level), false);

					child.heuristica = calcularHeuristica(child, importanciaProfundidad, ajusteAlCompas, relacionTonicasAtonas, importanciaMediaVarianza, mediaIdeal, varianzaIdeal, importanciaespacios);
					if(pq2.size()<MAXSIZEQUEUE)
						pq2.offer(new Node(child));
					System.err.println("añadido "+child.musicElementList.get(child.currentsize-1).getText()+child.musicElementList.get(child.currentsize-1).getDuration());
				}
				System.err.println("PAUSAS");
				Pause pausa = new Pause(' ');

				if(parent.level>=0)
					//si hay dos pausas seguidas no se incluyen en la cola
					if(parent.musicElementList.get(parent.currentsize-1) instanceof SyllableM &&  mp.get(parent.level+1) instanceof SyllableM){
						for(double i=0.125; i<=4 ;i*=2){
							for(double j=0.125; j<=4 ;j*=2){
								//pausa
								Node child = new Node(parent);
								child.level = parent.level+1;
								child.add(i,pausa, true);
								//nota
								child.add(j, mp.get(child.level), false);

								child.heuristica = calcularHeuristica(child, importanciaProfundidad, ajusteAlCompas, relacionTonicasAtonas, importanciaMediaVarianza, mediaIdeal, varianzaIdeal,importanciaespacios);
								if(pq2.size()<MAXSIZEQUEUE)
									pq2.offer(new Node(child));
								System.err.println("añadido "+child.musicElementList.get(child.currentsize-2).getText()+child.musicElementList.get(child.currentsize-2).getDuration()+" "+child.musicElementList.get(child.currentsize-1).getText()+child.musicElementList.get(child.currentsize-1).getDuration());
							}  
						}
					}
				//si hay una pausa, puede tener duración 0 (para que no suene a trompicones entre palabras)
				if(mp.get(parent.level+1) instanceof Pause){
					Node child = new Node(parent);
					child.level = parent.level+1;
					child.add(0,pausa, false);
					//nota	
					child.heuristica = calcularHeuristica(child, importanciaProfundidad, ajusteAlCompas, relacionTonicasAtonas, importanciaMediaVarianza, mediaIdeal, varianzaIdeal, importanciaespacios);
					if(pq2.size()<MAXSIZEQUEUE)
						pq2.offer(new Node(child));
					System.err.println("añadido "+child.musicElementList.get(child.currentsize-1).getText()+child.musicElementList.get(child.currentsize-1).getDuration());
				}
			}
			else{
				if(parent.level==numItems-1){
					//if(totalDur==0/* || totalDur==parent.value*/){
					//if(best == null || (best != null && (best.duration-parent.duration)%compas==0)){
						System.err.println("guardado");
						salir=true;
						best=parent;
					//}
				}
			}
		}
		System.err.println("Best");
		debug(best);
		writeRhythm(best);
	}


	//escribir en notación lilypond
	void writeRhythm(Node n){
//		System.err.println("DUR: "+n.duration);
		String partitura="\\version \"2.14.2\"\n%Test LilyPond (ejecutar lilypond archivo.ly) \n\\relative c'{\n";
		for(int i=0;i<mp.size()/*n.currentsize*/;i++){
			//nota
			if (mp.get(i) instanceof Pause)
				partitura+="r";
			else
				partitura+="c";
			if(i<n.currentsize){
				//duración, conversión a lilypond 4/duración de MusicElment
				partitura+= (n.musicElementList.get(i).getDuration()!=0) ? 4/n.musicElementList.get(i).getDuration() : 0;
			}
			partitura+=" ";
		}
		partitura+="\n}";

		//escribir en un archivo
		PrintWriter writer;
		try {
			writer = new PrintWriter("partitura.ly", "UTF-8");
			writer.println(partitura);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("HEU "+n.heuristica);
		System.err.println(partitura);
	}

	public int buscarTonica(int desde, Node nodo){
		int encontrado=0;
		for(int i=desde;i<nodo.musicElementList.size() && encontrado!=0;i++){
			if(nodo.musicElementList.get(i) instanceof SyllableM && ((SyllableM) nodo.musicElementList.get(i)).getTonic())
				encontrado = i;
		}
		return encontrado;
	}

	public double relaciontonicasatonas(Node nodo){
		int correctas = 0, total = 0, index = 0;
		int tonicaPos = buscarTonica(index, nodo);
		while(index < nodo.currentsize){
			//vemos si es una pausa
			if(nodo.musicElementList.get(index) instanceof Pause)
				if(nodo.pauses.get(index))
					tonicaPos = buscarTonica(index, nodo);
				else{
					index++;
				}
			if(tonicaPos != index && index < nodo.currentsize){//limites
				if(nodo.musicElementList.get(index).getDuration() < nodo.musicElementList.get(tonicaPos).getDuration())
					correctas++;
				total++;
			}
			index++;
		}
		return total==0? 0 : 1.0*correctas/total;
	}

}