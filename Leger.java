// Classen lege, som implimenterer interfacet lik, denen klassen er for leger som ikke har en komune avtale.


public class Leger implements Lik, Comparable<Leger> {
    private String navn;

    public Leger(String navn){
        this.navn=navn;

    }

    public String getNavnLege(){
        return navn;
    }

    public boolean samme(String a){
        if (navn.equalsIgnoreCase(a)){
            return true;
        }
        else {return false;}
    }



    public int compareTo(Leger i) {
          return this.navn.compareTo(i.getNavnLege());
    }
}
// Classen Fastlege, laget i etterkant for aa tilpassen neste oblig, denne klassen implementerer avtaleinterfacet
// istedet
class Fastlege extends Leger implements Avtale {
    private int avtalenummer;
    public Fastlege(String navn,int avtalenummer) {
        super(navn);
        this.avtalenummer=avtalenummer;
    }
    public int getAvtalenummer (){
        return avtalenummer;
    }
}

