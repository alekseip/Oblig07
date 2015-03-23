
public class YngsteForstResptListe extends EnkelResptListe {


    public void settInn(Resepter r){
        Node ny = new Node (r);
        if (getForste()==null){
            setForste(ny);
            setSiste(ny);
        }
        else {
            ny.neste=getForste();
            setForste(ny);
    }}
}
