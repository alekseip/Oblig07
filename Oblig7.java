// Skrevet av Aleksei, Carl
import java.util.Scanner;
import java.io.*;

public class Oblig7{
    public static void main(String args[]) throws FinnException {


        //ordrestyrt
        Scanner in = new Scanner(System.in);
        String info ="Oblig 7, INF1010\n\t--- Leger og Resepter ---\n\t1: Skriv data til fil\n\t2: Print all data til skjerm\n\t3: Opprett et nytt legemiddel\n\t4: Opprett en ny lege\n\t5: Oprett en ny person\n\t6: Opprett en ny resept\n\t7: Hent legemiddelet for en resept\n\t8: Velg en statistik til aa skirve ut\n\t0: Exit\n\tVelg 0-9: ";


        int a = 10;
        Ordre o = new Ordre();
        o.lesFil();
        while(a != 0){
            System.out.print(info);
            a = in.nextInt();
            switch(a){
                case 1: o.printDataTilFil();break;
                case 2: o.printData(); break;
                case 3: o.nyttLegemiddel(); break;
                case 4: o.nyLege(); break;
                case 5: o.nyPerson(); break;
                case 6: o.nyResept(); break;
                case 7: o.hentLegemiddel(); break;
                case 8: o.statestikk(); break;
            }
        }
    }
}
class Ordre{
    Scanner scanner = new Scanner(System.in);
    Tabell <Personer> personerTabell = new Tabell<Personer>(15);
    Tabell <Legemiddler> legemiddlerTabell = new Tabell<Legemiddler>(15);
    SorterEnkelListe<Leger> legerSliste = new SorterEnkelListe<Leger>();
    YngsteForstResptListe personResepter = new YngsteForstResptListe();
    EldsteForstResptListe legeResepter = new EldsteForstResptListe();
    public void lesFil(){
        try{
            Scanner fileIn = new Scanner(new File("data.txt"));

            while(fileIn.hasNext()){
                String s = fileIn.nextLine();
                if(s.startsWith("# Personer")){
                    s = fileIn.nextLine();
                    while(!s.startsWith("#")&& !s.equalsIgnoreCase("")){

                        String[] ord = s.split(", ");
                        int unik = Integer.parseInt(ord[0]);
                        String navn = ord[1];
                        long fnr = Long.parseLong(ord[2]);
                        String adr = ord[3];
                        int post = Integer.parseInt(ord[4]);
                        personerTabell.settIn(new Personer(unik,navn,fnr,adr,post), unik);

                        s = fileIn.nextLine();
                    }
                }

                if(s.startsWith("# Legemidler")){
                    s = fileIn.nextLine();
                    while(!s.startsWith("#")&& !s.equalsIgnoreCase("")){
                        String[] ord = s.split(", ");
                        int unik = Integer.parseInt(ord[0]);
                        String navn = ord[1];
                        String form = ord [2];
                        String type = ord [3];
                        int pris = Integer.parseInt(ord[4]);
                        int mengde = Integer.parseInt(ord[5]);
                        int virkestoff = Integer.parseInt(ord[6]);
                        int styrke = Integer.parseInt(ord[7]);
                        if (form.equalsIgnoreCase("pille")){
                            if (type.equalsIgnoreCase("a")){legemiddlerTabell.settIn(new TypeAPiller(navn,unik,pris,virkestoff,mengde,styrke),unik);}
                            if (type.equalsIgnoreCase("b")){legemiddlerTabell.settIn(new TypeBPiller(navn,unik,pris,virkestoff,mengde,styrke),unik);}
                            if (type.equalsIgnoreCase("c")){legemiddlerTabell.settIn(new TypeCPiller(navn,unik,pris,virkestoff,mengde,styrke),unik);}
                        }
                        if (form.equalsIgnoreCase("mikstur")){
                            if (type.equalsIgnoreCase("a")){legemiddlerTabell.settIn(new TypeAMixtur(navn,unik,pris,virkestoff,mengde,styrke),unik);}
                            if (type.equalsIgnoreCase("b")){legemiddlerTabell.settIn(new TypeBMixtur(navn,unik,pris,virkestoff,mengde,styrke),unik);}
                            if (type.equalsIgnoreCase("c")){legemiddlerTabell.settIn(new TypeCMixtur(navn,unik,pris,virkestoff,mengde,styrke),unik);}
                        }
                        s = fileIn.nextLine();

                    }
                }
                if(s.startsWith("# Leger")){
                    s = fileIn.nextLine();
                    while(!s.startsWith("#")&& !s.equalsIgnoreCase("")){
                        String[] ord = s.split(", ");
                        String navn = ord[0];
                        int avtalenr = Integer.parseInt(ord[1]);

                        if (avtalenr==0){legerSliste.settInnSortert(new Leger(navn));}
                        if (avtalenr!=0){legerSliste.settInnSortert(new Fastlege(navn,avtalenr));}
                        s = fileIn.nextLine();
                    }
                }
                if(s.startsWith("# Resepter")){
                    s = fileIn.nextLine();
                    while(!s.startsWith("#") && !s.equalsIgnoreCase("")){
                        String[] ord = s.split(", ");
                        int unik = Integer.parseInt(ord[0]);
                        String farge = ord[1];
                        int unikpersnr = Integer.parseInt(ord[2]);
                        String lege = ord[3];
                        int legemnr = Integer.parseInt(ord[4]);
                        int reit = Integer.parseInt(ord[5]);
                        if (farge.equalsIgnoreCase("hvit")){
                            personResepter.settInn(new Resepter(legemiddlerTabell.returner(legemnr),legerSliste.getE(lege),personerTabell.returner(unikpersnr),
                                    legemiddlerTabell.returner(legemnr).pris(),reit, unik));
                            legeResepter.settInn(new Resepter(legemiddlerTabell.returner(legemnr),legerSliste.getE(lege),personerTabell.returner(unikpersnr),
                                    legemiddlerTabell.returner(legemnr).pris(),reit, unik));

                        }
                        if (farge.equalsIgnoreCase("blaa"))
                        { personResepter.settInn(new BlaaResept(legemiddlerTabell.returner(legemnr),legerSliste.getE(lege),personerTabell.returner(unikpersnr),
                                legemiddlerTabell.returner(legemnr).pris(),reit, unik));
                            legeResepter.settInn(new BlaaResept(legemiddlerTabell.returner(legemnr),legerSliste.getE(lege),personerTabell.returner(unikpersnr),
                                    legemiddlerTabell.returner(legemnr).pris(),reit, unik));

                        }

                        s = fileIn.nextLine();
                    }
                }

            }
            fileIn.close();
        }catch(IOException e){System.out.println("No File");}
    }

    public String finnType(Legemiddler l){
        if (l instanceof TypeAMixtur){return "mikstur, a";}
        if (l instanceof TypeBMixtur){return "mikstur, b";}
        if (l instanceof TypeCMixtur){return "mikstur, c";}
        if (l instanceof TypeAPiller){return "pille, a";}
        if (l instanceof TypeBPiller){return "pille, b";}
        if (l instanceof TypeCPiller){return "pille, c";}
        return null;
    }

    public String finnMengde(Legemiddler l){
        if (l instanceof TypeAMixtur){return ((TypeAMixtur) l).getMixtur()+"";}
        if (l instanceof TypeBMixtur){return ((TypeBMixtur) l).getMixtur()+"";}
        if (l instanceof TypeCMixtur){return ((TypeCMixtur) l).getMixtur()+"";}
        if (l instanceof TypeAPiller){return ((TypeAPiller) l).getPilleribox()+"";}
        if (l instanceof TypeBPiller){return ((TypeBPiller) l).getPilleribox()+"";}
        if (l instanceof TypeCPiller){return ((TypeCPiller) l).getPilleribox()+"";}
        return null;
    }

    public int finnAvtle (Leger l){
        if (l instanceof Fastlege){return ((Fastlege) l).getAvtalenummer(); }
        return 0;
    }

    public String blaaTest(Resepter r){
        if (r instanceof BlaaResept){return "blaa";}
        return "hvit";
    }

    public void printData(){
        System.out.println("\tPersoner");
        for (Personer s : personerTabell){
            System.out.println(s.getUniknummer() + ", "+ s.getNavn()+ ", " + s.getFdnummer()+", "+ s.getAdresse() + ", " + s.getPostnr());
        }
        System.out.println("\tLegemidler");
        for (Legemiddler l : legemiddlerTabell){
            System.out.println(l.getUniknum()+", "+ l.getNavn()+", "+ finnType(l)+", " +l.pris()+", "+finnMengde(l)+", "+l.getVirkestoff()+", "+ l.getStyrke());
        }
        System.out.println("\tLeger");
        for (Leger l : legerSliste){
            System.out.println(l.getNavnLege() + ", " + finnAvtle(l));
        }
        System.out.println("\tResepter");
        for (Resepter r : legeResepter){
            System.out.println(r.getReseptnummer() + ", " + blaaTest(r) + ", " + r.getPasient().getUniknummer()+", " + r.getLege().getNavnLege() + ", " + r.getLegemiddel().getUniknum() + ", " + r.getReit());
        }
    }



    public void printDataTilFil(){
        try{
            PrintWriter write = new PrintWriter("data.txt");

            write.println("# Personer");

            for (Personer s : personerTabell){
                write.println(s.getUniknummer() + ", "+ s.getNavn()+ ", " + s.getFdnummer()+", "+ s.getAdresse() + ", " + s.getPostnr());
            }
            write.println();
            write.println("# Legemidler");
            for (Legemiddler l : legemiddlerTabell){
                write.println(l.getUniknum() + ", " + l.getNavn() + ", " + finnType(l) + ", " + l.pris() + ", " + finnMengde(l) + ", " + l.getVirkestoff() + ", " + l.getStyrke());
            }
            write.println();
            write.println("# Leger");
            for (Leger l : legerSliste){
                write.println(l.getNavnLege() + ", " + finnAvtle(l));
            }
            write.println();
            write.println("# Resepter");
            for (Resepter r : legeResepter){
                write.println(r.getReseptnummer() + ", " + blaaTest(r) + ", " + r.getPasient().getUniknummer() + ", " + r.getLege().getNavnLege() + ", " + r.getLegemiddel().getUniknum() + ", " + r.getReit());
            }
            write.println();
            write.println("# Stop");


            write.close();
        }catch(FileNotFoundException e){}
    }

    public void nyttLegemiddel (){
        int unik= 0;
        System.out.println("Skriv inn navn");
        String navn = scanner.nextLine();
        System.out.println("Pille/Mikstur");
        String form = scanner.nextLine().toLowerCase();
        System.out.println("Skriv type (a,b,c)");
        String type = scanner.nextLine();
        System.out.println("Skriv inn pris");
        int pris = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Mengde/Antall(int)");
        int mengde = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Skriv inn styrke(int)");
        int virkestoff = scanner.nextInt();
        System.out.println("Skriv inn styrke");
        int styrke = scanner.nextInt();
        scanner.nextLine();
        for (Legemiddler f : legemiddlerTabell){
            if (f!=null){unik++;}
        }
        if (form.equalsIgnoreCase("pille")){
            if (type.equalsIgnoreCase("a")){legemiddlerTabell.settIn(new TypeAPiller(navn,unik,pris,virkestoff,mengde,styrke),unik);}
            if (type.equalsIgnoreCase("b")){legemiddlerTabell.settIn(new TypeBPiller(navn,unik,pris,virkestoff,mengde,styrke),unik);}
            if (type.equalsIgnoreCase("c")){legemiddlerTabell.settIn(new TypeCPiller(navn,unik,pris,virkestoff,mengde,styrke),unik);}
        }
        if (form.equalsIgnoreCase("mikstur")){
            if (type.equalsIgnoreCase("a")){legemiddlerTabell.settIn(new TypeAMixtur(navn,unik,pris,virkestoff,mengde,styrke),unik);}
            if (type.equalsIgnoreCase("b")){legemiddlerTabell.settIn(new TypeBMixtur(navn,unik,pris,virkestoff,mengde,styrke),unik);}
            if (type.equalsIgnoreCase("c")){legemiddlerTabell.settIn(new TypeCMixtur(navn,unik,pris,virkestoff,mengde,styrke),unik);}
        }
    }

    public void nyLege (){
        System.out.println("Skriv inn navn");
        String navn = scanner.nextLine();
        System.out.println("Avtale nummer (0 = ingen)");
        int avtalenummer = scanner.nextInt();
        scanner.nextLine();
        if (avtalenummer==0){legerSliste.settInnSortert(new Leger(navn));}
        if (avtalenummer!=0){legerSliste.settInnSortert(new Fastlege(navn,avtalenummer));}
    }

    public void nyPerson (){
        System.out.println("Skriv inn navn");
        String navn = scanner.nextLine();
        System.out.println("Skriv fødselsnummer");
        long fdnr = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Skriv inn adresse");
        String adresse = scanner.nextLine();
        System.out.println("Skriv inn postnummer");
        int postnr = scanner.nextInt();
        scanner.nextLine();
        int unik = 0;
        for (Personer p : personerTabell ){
            if (p != null){unik++;}
        }
        personerTabell.settIn(new Personer(unik,navn,fdnr, adresse, postnr),unik);
    }

    public void nyResept (){
        System.out.println("Farge(blaa,hvit)");
        String farge = scanner.nextLine().toLowerCase();
        System.out.println("Skriv inn uniknummer");
        int unikpn = 0;
        for (Personer p : personerTabell ){
            if (p != null){unikpn++;}
        }
        int unikpersnr = scanner.nextInt();
        scanner.nextLine();
        if (unikpersnr>unikpn){
            System.out.println("nummeret er for høyt, skriv et tall under "+unikpn);
            unikpersnr = scanner.nextInt();
            scanner.nextLine();
        }
        System.out.println("Skriv inn lege navn");
        String legeNavn = scanner.nextLine();
        if (legerSliste.getE(legeNavn)==null){
            System.out.println("Legen finnes ikke, sett inn ny lege");
            nyLege(); return;}
        System.out.println("Skriv inn legemiddel nummer");
        int legemnr = scanner.nextInt();
        scanner.nextLine();
        int teller = 0;
        for (Legemiddler l : legemiddlerTabell){
            if (l!=null){teller++;}

        }
        if (legemnr>teller){
            System.out.println("Legemidlet finnes ikke, opprett nytt legemiddel");
            nyttLegemiddel();
            return;
        }
        System.out.println("Antall uttak (reit)");
        int reit=scanner.nextInt();
        scanner.nextLine();
        int unik=0;
        for (Resepter m : legeResepter){
            if(m!=null){unik++;}
        }
        if (farge.equalsIgnoreCase("hvit")){
            personResepter.settInn(new Resepter(legemiddlerTabell.returner(legemnr),legerSliste.getE(legeNavn),personerTabell.returner(unikpersnr),
                    legemiddlerTabell.returner(legemnr).pris(),reit, unik));
            legeResepter.settInn(new Resepter(legemiddlerTabell.returner(legemnr),legerSliste.getE(legeNavn),personerTabell.returner(unikpersnr),
                    legemiddlerTabell.returner(legemnr).pris(),reit, unik));

        }
        if (farge.equalsIgnoreCase("blaa"))
        { personResepter.settInn(new BlaaResept(legemiddlerTabell.returner(legemnr),legerSliste.getE(legeNavn),personerTabell.returner(unikpersnr),
                legemiddlerTabell.returner(legemnr).pris(),reit, unik));
            legeResepter.settInn(new BlaaResept(legemiddlerTabell.returner(legemnr),legerSliste.getE(legeNavn),personerTabell.returner(unikpersnr),
                    legemiddlerTabell.returner(legemnr).pris(),reit, unik));

        }


    }
    public void hentLegemiddel () throws FinnException {
        System.out.println("Tast inn reseptnummer");
        int nummer = scanner.nextInt();
        scanner.nextLine();
        int teller=0;
        for(Resepter r : legeResepter){
            if (r!=null){teller++;}
        }
        if (nummer > teller){
            System.out.println("Resepten finnes ikke");}

        if (legeResepter.finn(nummer).getReit()>0){
            System.out.println("Here er legemidlet:"+ legeResepter.finn(nummer).getLegemiddel().getNavn());
            legeResepter.finn(nummer).setReit(legeResepter.finn(nummer).getReit()-1);
        }

        if (legeResepter.finn(nummer).getReit()==0){
            System.out.println("Ugyldig resept");}

    }

    public void statestikk(){
        System.out.print("1.Vanedannede i Oslo\n2.Personens blaa\n3.Mikstur for legen\n4.Missbruk\n\t:");
        int innputt = scanner.nextInt();
        scanner.nextLine();

            switch (innputt){
                case 1: vanedannende(); break;
                case 2: blaaPerson();break;
                case 3: miksturLegene();break;
                case 4: missbruk();break;
            }


    }
    public void vanedannende (){
        int antallvanedannende=0;
        int antallOslo=0;
        for (Resepter r : legeResepter){
            if (r.getLegemiddel() instanceof TypeBPiller|| r.getLegemiddel() instanceof TypeBMixtur){
                antallvanedannende++;
                if (r.getPasient().getPostnr()<1300){
                    antallOslo++;
                }

            }
        }
        System.out.println("Antall vanedanende er: "+antallvanedannende);
        System.out.println("Antall vanedanende i Oslo: " +antallOslo);
    }
    public void blaaPerson (){
        System.out.println("Skriv inn personnummer/uniknummer");
        long nummer = scanner.nextLong();
        scanner.nextLine();
        int test =0;
        for (Personer p : personerTabell){
            if (nummer == p.getFdnummer() || nummer == p.getUniknummer()){
                test=1;
                for (Resepter r : personResepter){
                    if (nummer == r.getPasient().getFdnummer()||nummer == r.getPasient().getUniknummer()){
                        if (r instanceof BlaaResept){
                            r.printResept("blaa");
                        }
                    }
                }
            }
        }
        if (test==0){System.out.println("Finner ikke person");}
    }

    public void miksturLegene (){
        System.out.println("Skriv inn navnet til legen");
        String navn = scanner.nextLine();
        int mengdevirkestoff = 0;
        int mengdemikstur = 0;
        int mengdepiller = 0;
        for (Resepter r : legeResepter){
            if (r.getLege().getNavnLege().equalsIgnoreCase(navn)){
                if (r.getLegemiddel() instanceof Mikstur){
                       if (r instanceof BlaaResept){
                           r.printResept("blaa");
                       }
                       else {r.printResept("hvit");}
                }

            mengdevirkestoff  = mengdevirkestoff+ r.getLegemiddel().getVirkestoff();

                if (r.getLegemiddel() instanceof Mikstur ){
                    mengdemikstur = mengdemikstur + r.getLegemiddel().getVirkestoff();
                }
                if (r.getLegemiddel() instanceof Piller){
                    mengdepiller = mengdepiller+r.getLegemiddel().getVirkestoff();
                }
            }
        }
        System.out.println("Totalt virkestoff " + mengdevirkestoff);
        System.out.println("Totalt virkestoff i mikstur " + mengdemikstur);
        System.out.println("Totalt virkestoff i piller " + mengdepiller);
    }
    public void missbruk (){
       for (Leger l : legerSliste )   {
           int antallnark = 0;
        for (Resepter r : legeResepter ){
               if (r.getLege() == l){
                   if (r.getLegemiddel() instanceof TypeAMixtur || r.getLegemiddel() instanceof TypeAPiller){
                       antallnark++;

                   }

               }
            }
        if (antallnark > 0){
            System.out.println(l.getNavnLege() +" antall narkotiske resepter "+ antallnark);
        }
    }
        System.out.println("...........................................");
        for (Personer p : personerTabell){
        int antallnarkotika = 0;

       for (Resepter r : personResepter){
           if(r.getPasient() == p){
               if (r.getLegemiddel() instanceof TypeAMixtur || r.getLegemiddel() instanceof TypeAPiller){
                   antallnarkotika++;
               }
           }
       }
        if (antallnarkotika>0){
            System.out.println(p.getNavn() + " antall narkotiske resepter " + antallnarkotika);
        }
    }


    }
}
/* Hvis denne blir brukt mye, bør man lage en egen liste for å finne antall lettere, og få det til å
mer oversiktlig ut ved utskriving. Burde skrive en adversel hvis en person har mere en x antall narkotiske resepter
*/