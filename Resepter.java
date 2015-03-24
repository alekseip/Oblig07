// Classe Respeter
public class Resepter {
    private int reseptnummer;
    private Legemiddler legemiddel;
    private Leger lege;
    private Personer pasient;
    private int pris;
    private int reit;

    public Resepter(Legemiddler legemiddel, Leger lege, Personer pasient, int pris, int reit, int reseptnummer){
        this.legemiddel=legemiddel;
        this.lege = lege;
        this.pasient=pasient;
        this.pris=pris;
        this.reit=reit;
        this.reseptnummer=reseptnummer;
    }

    public int getReseptnummer(){return reseptnummer;}
    public Legemiddler getLegemiddel(){return legemiddel;}
    public Leger getLege(){return lege;}
    public Personer getPasient(){return pasient;}
    public int getPris(){return pris;}
    public int getReit(){return reit;}

}
// Valgte aa lage BlaaResept som en subclasse for Resepter, resepter i dette tilfelle vill vaere hviteresepter, men kan
// muligens lage en subclasse for det, og lage resptclassen som en abstrakt klasse.
class BlaaResept extends Resepter{
        private int pris;
        private int reseptnummer;
    public BlaaResept(Legemiddler legemiddel, Leger lege, Personer pasient, int pris, int reit, int reseptnummer){
        super(legemiddel,lege,pasient,pris,reit,reseptnummer);
        this.pris=pris;
        this.pris=0;
        this.reseptnummer = reseptnummer;
    }
    public int getPris(){return pris;}// hvis det ikke fungerer sett this.pris
}
