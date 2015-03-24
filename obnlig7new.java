import java.util.Scanner;
import java.io.*;

public class Oblig7{
    public static void main(String args[]){


        //ordrestyrt
        Scanner in = new Scanner(System.in);
        String info ="Oblig 7, INF1010\n\t--- Leger og Resepter ---\n\t1: Les in data fra fil\n\t2: Skriv data til fil\n\t3: Print all data til skjerm\n\t4: Opprett et nytt legemiddel\n\t5: Opprett en ny lege\n\t6: Oprett en ny person\n\t7: Opprett en ny resept\n\t8: Hent legemiddelet for en resept\n\t9: Velg en statistik til aa skirve ut\n\t0: Exit\n\tVelg 0-9: ";


        int a = 10;
        Ordre o = new Ordre();

        while(a != 0){
            System.out.print(info);
            a = in.nextInt();
            switch(a){
                case 1: o.lesFil(); break;
                case 2: o.printDataTilFil();break;
                case 3: o.printData(); break;
                case 4: o.nyttLegemiddel(); break;
                case 5: o.nyLege(); break;
                case 6: o.nyPerson(); break;
                case 7: o.nyResept(); break;
                case 8: System.out.println("hente legemiddelet for en resept"); break;
                case 9: System.out.println("valg av statistiker"); break;
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
}
