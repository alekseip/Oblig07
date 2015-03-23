import java.util.Iterator;

public class SorterEnkelListe<E extends Comparable<E> & Lik> implements AbstraktSortertEnkelListe<E>, Iterable<E> {
    private Node forste;

    public Iterator<E> iterator()
    {return new ListeIterator();}

    public void settInnSortert(E e){
	Node tmp = forste;
	if (forste==null){
	    forste=new Node(e);
	}
	else if (forste.neste==null){
	    if(forste.data.compareTo(e)<0){forste.neste = new Node(e);}
	    else{ Node t = forste; forste = new Node(e); forste.neste=t;}}

	else{
	    while (tmp.neste !=null){
		if (tmp.data.compareTo(e)<0 && tmp.neste.data.compareTo(e)>0){
		    Node n = tmp.neste;
		    tmp.neste = new Node(e);
		    tmp.neste.neste= n;
		    break;

		}
		tmp=tmp.neste;
		if(tmp.neste == null){tmp.neste = new Node(e); break;}
	    }
	}
    }



    public E sookEtterNookel(String a) {
	Node temp = forste;
	while (temp.neste!=null){
	    if (forste.neste==null){
		if (a.equalsIgnoreCase(forste.data.toString())){
		    return forste.data;
		}
	    }

	    else{
		if (a.equalsIgnoreCase(temp.data.toString())){return temp.data;}
		temp=temp.neste;
	    }}
	return null;
    }

    public E getE (String s){
        Node node = forste;
            while(node!=null){
                if (node.data.samme(s)){return node.data;}
                node=node.neste;
            }
           return null;
    }

    public boolean samme(String a) {
	return false;
    }


    private class Node{
	E data;
	Node neste;
	public Node (E data){this.data=data;}


    }

    class ListeIterator implements Iterator<E>{
	Node tmp = forste;
	@Override
	    public boolean hasNext() {
	    return tmp!=null;
	}


	public E next() {
	    E denne = tmp.data;
	    tmp = tmp.neste;
	    return denne;
	}

	public void remove() {

	}
    }}