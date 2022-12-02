import java.math.BigInteger;
import java.util.*;
import java.security.SecureRandom;

public class FIATSHAMIR {



    public BigInteger Gyorshatvany(BigInteger alap, BigInteger kitevo, BigInteger modulus) {
        BigInteger eredmeny = BigInteger.ONE;
        BigInteger apow = alap;

        while (!kitevo.equals(BigInteger.ZERO)) {
            if (kitevo.and(BigInteger.ONE).equals(BigInteger.ONE)) {

                eredmeny = (eredmeny.multiply(apow)).mod(modulus);
            }
            kitevo = kitevo.shiftRight(1);
            apow = apow.multiply(apow).mod(modulus);
        }
        return eredmeny;
    }

    private BigInteger randomszám(BigInteger min, BigInteger max) {
        BigInteger a;

        do {
            a = new BigInteger(min.bitLength(), new SecureRandom());
        } while (a.compareTo(min) < 0 || a.compareTo(max) > 0);

        return a;
    }


    private BigInteger Knumber(BigInteger min, BigInteger max) {
        BigInteger a;

        do {
            a = new BigInteger(min.bitLength(), new SecureRandom());
        } while (a.compareTo(min) < 0 || a.compareTo(max) > 0);

        return a;
    }

    public BigInteger generateSecureRandomNumber(Integer bitLength) {

        SecureRandom srg = new SecureRandom();
        return new BigInteger(bitLength, srg);
    }

    private boolean millerRabin(BigInteger szam, int tesztszam) {
        if (szam.equals(BigInteger.ZERO) || szam.equals(BigInteger.ONE) || szam.equals(new BigInteger("4")))

            return false;

        if (szam.equals(new BigInteger("2")) || szam.equals(new BigInteger("3")))


            return true;

        BigInteger m = szam.subtract(BigInteger.ONE);
        while (m.mod(new BigInteger("2")).equals(BigInteger.ZERO))
            m = m.divide(new BigInteger("2"));

        int i;
        BigInteger a = BigInteger.ZERO;
        BigInteger x = BigInteger.ZERO;

        for (i = 0; i < tesztszam; i++) {
            BigInteger max = szam.subtract(new BigInteger("2"));
            BigInteger min = new BigInteger("2");
            a = randomszám(min, max).add(min);

            x = Gyorshatvany(a, m, szam);

            if (x.equals(BigInteger.ONE) || x.equals(szam.subtract(BigInteger.ONE)))
                continue;

            while (!m.equals(szam.subtract(BigInteger.ONE))) {
                x = x.multiply(x).mod(szam);
                m = m.multiply(new BigInteger("2"));

                if (x.equals(BigInteger.ONE))
                    return false;

                if (x.equals(szam.subtract(BigInteger.ONE)))
                    continue;
            }
            return false;
        }
        return true;
    }


    private static BigInteger inverz(BigInteger a, BigInteger m) {
        BigInteger m0 = m;
        BigInteger x = BigInteger.ONE;
        BigInteger y = BigInteger.ZERO;
        if (m.equals(BigInteger.ONE))
            return BigInteger.ZERO;
        //ittjooooooo
        BigInteger q, b;
        while (a.compareTo(BigInteger.ONE) > 0) {
            //       System.out.println("HOLROSSZ1");
            q = a.divide(m);
            b = m;
            m = a.mod(m);
            a = b;
            b = y;
            y = x.subtract(q.multiply(y));
            x = b;
        }
        if (x.compareTo(BigInteger.ZERO) < 0)
            x = x.add(m0);
        //     System.out.println("HOLROSSZ2");
        return x;
    }


    public static BigInteger Euklidesz(BigInteger a, BigInteger b) {
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger r = a.mod(b);
            a = b;
            b = r;
        }
        return a;
    }


    public static BigInteger Kibovitetteuk(BigInteger a, BigInteger b) {
        BigInteger x0 = BigInteger.ONE, x1 = BigInteger.ZERO, y0 = BigInteger.ZERO, y1 = BigInteger.ONE, x = null, y = null, n = BigInteger.ONE;


        while (!b.equals(BigInteger.ZERO)) {
            //    System.out.println("VEGTELEN");
            BigInteger r = a.mod(b);
            BigInteger q = a.divide(b);
            a = b;
            b = (r);
            x = x1;
            y = y1;
            x1 = q.multiply(x1).add(x0);
            y1 = q.multiply(y1).add(y0);
            x0 = x;
            y0 = y;
            n = n.negate();
        }
        x = n.multiply(x0);
        y = n.negate().multiply(y0);
        return a;
    }


    private BigInteger primgeneral() {
        BigInteger eredmeny = generateSecureRandomNumber(7);
        while (!millerRabin(eredmeny, 3))      //ITTJOMEG
            eredmeny = generateSecureRandomNumber(7);
        return eredmeny;
    }


    private List<BigInteger> clista(BigInteger uzenet, BigInteger p, BigInteger q, BigInteger d) {

        List<BigInteger> c = new ArrayList<>();

        BigInteger C1, C2, D1, D2;

        C1 = uzenet.mod(p);
        C2 = uzenet.mod(q);

        D1 = d.mod(p.subtract(BigInteger.ONE));
        D2 = d.mod(q.subtract(BigInteger.ONE));


        c.add(Gyorshatvany(C1, D1, p));
        c.add(Gyorshatvany(C2, D2, q));


        return c;
    }

    private List<BigInteger> mlista(BigInteger p, BigInteger q, BigInteger message, BigInteger d) {

        List<BigInteger> m = new ArrayList<>();

        m.add(p);
        m.add(q);

        return m;
    }

    private List<BigInteger> mlista2(BigInteger p, BigInteger q) {

        List<BigInteger> m2 = new ArrayList<>();

        m2.add(p);
        m2.add(q);

        return m2;
    }

    private BigInteger root(BigInteger p) {

        BigInteger mi = BigInteger.ZERO;
        BigInteger helperl = p.subtract(BigInteger.ONE);
        List<BigInteger> m2 = new ArrayList<>();

        for (BigInteger i =BigInteger.ONE; (i.compareTo(helperl) < 0) ; i = i.add(BigInteger.ONE)) {
            for (BigInteger j =BigInteger.ZERO; (j.compareTo(p) < 0) ; j = j.add(BigInteger.ONE)) {
                m2.add(Gyorshatvany(i,j,p));
            }
        }
        BigInteger Chooser = generateSecureRandomNumber(10);
        int Number = Chooser.intValue();
        mi = m2.get(Number);

        BigInteger bi = BigInteger.valueOf(mi.intValue());


        return bi;
    }




    private static BigInteger Kinaimaradek(List<BigInteger> c, List<BigInteger> m) {
        BigInteger M = m.stream().reduce(BigInteger.ONE, BigInteger::multiply);
        BigInteger x = BigInteger.ZERO;

        for (int i = 0; i < c.size(); i++) {
            BigInteger mi = M.divide(m.get(i));
            BigInteger yi = inverz(mi, m.get(i));
            x = x.add(c.get(i).multiply(mi).multiply(yi));
        }


        x = x.mod(M);
        return x;
    }

    private static BigInteger Keys(){




        return BigInteger.ONE;
    }

    private static BigInteger sign(BigInteger n){
        BigInteger r,u,e,ek,s;

        r = fiatshamir.generateSecureRandomNumber(5);
        u = fiatshamir.Gyorshatvany(r,BigInteger.ONE.add(BigInteger.ONE),n);
        s = r;


        return BigInteger.ONE;
    }

    private static BigInteger verify(BigInteger s,BigInteger n) {
        BigInteger e, ek, w, es;

        w = fiatshamir.Gyorshatvany(s, BigInteger.ONE.add(BigInteger.ONE), n);


        return e.equals(es);

    }

    private static BigInteger FkeyGenerator(){
        BigInteger p, q,  n, d;

        p = fiatshamir.primgeneral();
        q = fiatshamir.primgeneral();
        n = p.multiply(q);




        return BigInteger.ONE;
    }



    public static void main(String[] args) {
        BigInteger n,k,s,v,e,souts;
        FIATSHAMIR fiatshamir = new FIATSHAMIR();

        n = fiatshamir.FkeyGenerator(20);
        k = BigInteger.valueOf(10);
        (s, v) = fiatshamir.Keys(n, k);
        (e, s) = fiatshamir.sign(n, k, s);
        souts = fiatshamir.(verify((e, s), n, k, v);
        System.out.println(souts);





    }

}


