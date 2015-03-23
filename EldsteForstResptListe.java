public class EldsteForstResptListe extends EnkelResptListe {



    public void settInn(Resepter r){
        Node ny = new Node (r);
        if (getForste()==null){
            setForste(ny);
            setSiste(ny);
        }
        else{
            setSisteNeste(ny);
            setSiste(ny);
        }
    }
}
