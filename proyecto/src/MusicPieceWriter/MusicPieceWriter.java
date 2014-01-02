package MusicPieceWriter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import MusicPiece.MusicElement;
import MusicPiece.MusicPiece;
import MusicPiece.Pause;
import MusicPiece.SyllableM;

//Conversión a Lilypond
//Generacion greedy ritmo
//Generación greedy melodía
//Etiquetas (tempo, compás, modalidad, tonalidad, registro)
//Armonía

public class MusicPieceWriter {
	int compas;
	double K;
	List<MusicElement> mp, s;
	int numItems;
	Queue<Node> q;
	Node best;
	
	class Node {
	    int level;
	    double size, value, bound;
	    Vector<Double> duracion;
	    
	    protected Node( ) {
			level = -1;
			size = 0.0;
			value = 0.0;
			bound = 0.0;
			duracion = null;
	    }
	    
	    public Node(Node original) {
	        super();
	        this.level = original.level;
	        this.size = original.size;
	        this.value = original.value;
	        this.bound = original.bound;
	        this.copyList(original.duracion);
	    }
	
	    protected void copyList(Vector<Double> v) {//usar otra funcion
	      if (v == null || v.isEmpty( ) )
	    	  duracion = new Vector<Double>( );
	      else
	    	  duracion = new Vector<Double>(v);
	    }
	    
	    protected void add(double index) {
	    	duracion.add(index);
	    	value+=index;
	    	if(s.get(level) instanceof SyllableM)
		    	if(((SyllableM) s.get(level)).getTonic())
		    		bound -= 1;
		    	else 
		    		bound -= 0.5;
	    	else
	    		bound -= 0.5;
	    	bound += index;
	    }
	    
	    private double bound(int item) {
		    int k = item ;
		    double bound = value;
		    while (k < mp.size() ) {
		    	if(mp.get(k) instanceof SyllableM)
			    	if(((SyllableM) mp.get(k)).getTonic())
			    		bound += 1;
			    	else 
			    		bound += 0.5;
		    	else
		    		bound += 0.5;
		    	k++;
		    }
		    return bound; 
		}
	    
	  }  

	  public MusicPieceWriter(MusicPiece m, int comp) {
		    mp = m.getElements();
		    compas = comp;
		    Node root = new Node( );
		    root.bound = root.bound(0);
		    q = new LinkedList<Node>();
		    q.offer(root);
		    K = root.bound * 3;
	  }
		  
	  private void debug(Node nodo){
		  for(int i=0;i<nodo.level+1;i++){
			  s.get(i).depurar();
			  System.err.print(nodo.duracion.elementAt(i)+" ");
		  }
	  }

	  private boolean cumpleRestricciones(Node nodo){
		  boolean valid=true;
		//comprobamos que no se supera una cota máxima
		  if (nodo.bound > K){ 
			  valid=false;
		  }
		//comprobamos que las sílabas tónicas van en tiempos fuertes
		  if(s.get(nodo.level) instanceof SyllableM) 
			  if(((SyllableM)s.get(nodo.level)).getTonic()){
				  double tono = nodo.value - (double)nodo.duracion.elementAt(nodo.level);
				  if( tono != (int)tono || tono%compas != 0){
					  valid=false;
				  }
		  }
		//si es un espacio o si es la última sílaba de la frase, comprobamos las duraciones de las sílabas de la palabra para ver que las tónicas duran más
		  if(valid && (s.get(nodo.level) instanceof Pause || nodo.level==numItems-1)){
			  double ton=4, aton=0;
			  int i = nodo.level-1;
			  if(nodo.level==numItems-1)
				  i++;
			  while(i >= 0 && !(s.get(i) instanceof Pause)){//s[i]!=' '
				  if(((SyllableM) s.get(i)).getTonic() && (double)nodo.duracion.elementAt(i) < ton)//check instanceof?
					  ton = (double)nodo.duracion.elementAt(i);
				  else if((double)nodo.duracion.elementAt(i) > aton)
					  aton = (double)nodo.duracion.elementAt(i);
				  if(ton <= aton){
					  valid=false;
				  }
				  i--;
			  }
		  }
		  return valid;
	  }
	  
	  public void generateRhythm(SyllableM s1, SyllableM s2){
		  //regenerar ritmo?
		  double totalDur=0;
		  if(s!=null&&s.get(0).equals(s1) && s.get(s.size()-1).equals(s2)){//si se regenera la duración total es la misma para no descuadrar las notas siguientes
			  System.err.println("regenerar");
			  totalDur=best.value;
		  }
		  else{
			  int index=0, ini;
			  numItems = mp.size();
			  while(index<numItems&&!mp.get(index).equals(s1))
				  index++;
			  ini=index;
			  index++;
			  while(index<numItems&&!mp.get(index).equals(s2))
				  index++;
			  s=mp.subList(ini, index+1);
			  numItems = s.size();
		  }
		  boolean salir;
		  salir=false;
		  Node parent =new Node();
		  while (!q.isEmpty( ) && !salir) {
			  parent = (Node)q.poll( );
		      if (parent.level<numItems-1) {
		    	  for(int i=1;i<7;i++){
		    		  Node child = new Node(parent);
		    		  //se le asigna una duración. 
		    		  child.level = parent.level+1;
		    		  child.add(i*0.5); //revisar como se generan los hijos
		    		  //si es válido se guarda en la cola
		              if(cumpleRestricciones(child)){
		            	  q.offer(child);
		              }
		    	  }
		      }
		      else{
		    	  if(parent.level==numItems-1){
		    		  if(totalDur==0 || totalDur==parent.value){
				    	  salir=true;
				    	  best=parent;
		    		  }
		    	  }
		      }
		  }
		  debug(best);
		  writeRhythm(best);
	  }

	String calcDuration(double dur){
// 		dur entre 0.5 y 3
/*		lilypond
 		1 redonda
		2 blanca
		4 negra
		8 corchea
		16 semicorchea
		32 fusa
		64 semifusa
conversión
		0.5		8
		1		4
		1.5		4.
		2		2
		2.5		2.
		3		1
*/		
		String lilyDur = "8";
		if(dur == 1)
			lilyDur = "4";
		else if(dur == 1.5)
			lilyDur = "4.";
		else if(dur == 2)
			lilyDur = "2";
		else if(dur == 2.5)
			lilyDur = "2.";
		else if(dur == 3)
			lilyDur = "1";
		return lilyDur;
	}

    //escribir en notación lilypond
	void writeRhythm(Node n){
		String partitura="\\version \"2.14.2\"\n%Test LilyPond (ejecutar lilypond archivo.ly) \n\\relative c'{\n";
		for(int i=0;i<numItems;i++){
			//nota
			if (mp.get(i) instanceof Pause) 
				partitura+="r";
			else
				partitura+="c";
			//duración
			partitura+= calcDuration(n.duracion.elementAt(i));
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
		  System.out.println(partitura);
	  }	  
}
