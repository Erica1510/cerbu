package org.example.MD4;

import java.util.ArrayList;

public class Main {

    public static final String report = "report.txt";
    public static final String reportF = "reportF.txt";
    public static final String reportG = "reportG.txt";
    public static final String reportH = "reportH.txt";
    public static final String reportFinal = "reportFinal.txt";

    static final String message = "H";


    public static StringBuilder formalOutput(ArrayList<Integer> arrayList){
        StringBuilder string = new StringBuilder();
        for (Integer i:arrayList) {
            string.append(i);
        }
        return string;
    }

    public static void output(ArrayList<Integer> arrayList){
        int x = 0;
        for (Integer i:arrayList) {
            if (x == 7){
                System.out.print(" ");
                x = 0;
            }
            else x++;
        }
    }

    static ArrayList<Integer> bin(long s){
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < 64; i++)
        {
            long c = (long)Math.pow(2,63) + 1;
            result.add((s & c) == 0 ? 0 : 1);
            s <<= 1;
        }
        return result;
    }

    static ArrayList<Integer> bin(int s){
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < 32; i++)
        {
            int c = (int)Math.pow(2,31) + 1;
            result.add((s & c) == 0 ? 0 : 1);
            s <<= 1;
        }
        return result;
    }

    static ArrayList<Integer> bin(String s)
    {
        byte[] bytes = s.getBytes();
        ArrayList<Integer> result = new ArrayList<>();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                result.add((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return result;
    }

    public static ArrayList<Integer> expandBits(String message){
        ArrayList bits = bin(message);
        while (bits.get(0).equals(0)){
            bits.remove(0);
        }
        int len = bits.size();
        System.out.println("bits size = " + bits.size());
        bits.add(1);
        int bitsLength = bits.size();
        int X = 448 - (bitsLength % 512);
        for (int i = 0; i < X; i++) {
            bits.add(0);
        }
        bits.addAll(bin((long)len));
        return bits;
    }

    public static ArrayList<Integer> getMString(ArrayList<Integer> bits,int number) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int k = 0; k < 32; k++) {
            result.add(bits.get(number * 32 + k));
        }
        return result;
    }

    public static int sumMod32(long a,long b){
        return (int)((a + b) % (long)Math.pow(2,32));
    }

    public static void main(String[] args) throws Exception {
        FileWork.reportCreate(reportF);
        FileWork.reportCreate(reportG);
        FileWork.reportCreate(reportH);
        FileWork.reportCreate(report);
        FileWork.reportCreate(reportFinal);

        int A = (0x67 << (3 * 8)) + (0x45 << (2 * 8)) + (0x23 << 8) + 0x01;
        int B = (0xef << (3 * 8)) + (0xcd << (2 * 8)) + (0xab << 8) + 0x89;
        int C = (0x98 << (3 * 8)) + (0xba << (2 * 8)) + (0xdc << 8) + 0xfe;
        int D = (0x10 << (3 * 8)) + (0x32 << (2 * 8)) + (0x54 << 8) + 0x76;
            FileWork.reportWriteln(report,"A = " + (long)A + " = " + Integer.toBinaryString(A));
            FileWork.reportWriteln(report,"B = " + (long)B + " = " + Integer.toBinaryString(B));
            FileWork.reportWriteln(report,"C = " + (long)C + " = " + Integer.toBinaryString(C));
            FileWork.reportWriteln(report,"D = " + (long)D + " = " + Integer.toBinaryString(D));
        Word wordA = new Word(bin(A));
        Word wordB = new Word(bin(B));
        Word wordC = new Word(bin(C));
        Word wordD = new Word(bin(D));
            FileWork.reportWriteln(report,"wordA.word = " + wordA.getLong() + " = " + formalOutput(wordA.word));
            FileWork.reportWriteln(report,"wordB.word = " + wordB.getLong() + " = " + formalOutput(wordB.word));
            FileWork.reportWriteln(report,"wordC.word = " + wordC.getLong() + " = " + formalOutput(wordC.word));
            FileWork.reportWriteln(report,"wordD.word = " + wordD.getLong() + " = " + formalOutput(wordD.word));
            FileWork.reportWrite(report,"\n");

        ArrayList<Word> stack = new ArrayList<>();
        stack.add(wordA);
        stack.add(wordB);
        stack.add(wordC);
        stack.add(wordD);
//        int ix = 0;
//        for (Word w:stack) {
//            FileWork.reportWriteln(report,"stack(" + ix++ + ") = " + w.getLong() + " = " + formalOutput(w.word));
//        }


        ArrayList<Integer> bits = expandBits(message);
            FileWork.reportWriteln(report,"bits = " + formalOutput(bits));


        int N = bits.size() / 32;
        for (int i = 0; i < N/(16 - 1); i++) {
            Word MWords = new Word();
            Word tempWord = new Word();
            Word AA = stack.get(0);
            Word BB = stack.get(1);
            Word CC = stack.get(2);
            Word DD = stack.get(3);
            Word F = new Word();
            int[] Fs = {3,7,11,19};
            FileWork.reportWriteln(reportF,"Start stack:");

            int ix = 0;

            for (Word w:stack) {
                FileWork.reportWriteln(reportF,"stack(" + ix++ + ") = " + w.getLong() + " = " + formalOutput(w.word));
            }
            for (int j = 0; j < 16; j++) {
                    FileWork.reportWriteln(reportF,"J = " + j);
                    FileWork.reportWriteln(reportF,"\t  B = " + formalOutput(stack.get(1).word));
                    FileWork.reportWriteln(reportF,"\t  C = " + formalOutput(stack.get(2).word));
                    FileWork.reportWriteln(reportF,"\tand = " + formalOutput(Word.and(stack.get(1).word,stack.get(2).word)));
                    FileWork.reportWriteln(reportF);
                    FileWork.reportWriteln(reportF,"\t  !B= " + formalOutput(stack.get(1).not()));
                    FileWork.reportWriteln(reportF,"\t  D = " + formalOutput(stack.get(3).word));
                    FileWork.reportWriteln(reportF,"\tand = " + formalOutput(Word.and(stack.get(1).not(),stack.get(3).word)));
                    FileWork.reportWriteln(reportF);
                F.word = Word.or(
                                Word.and(stack.get(1).word,stack.get(2).word),
                                Word.and(stack.get(1).not(),stack.get(3).word)
                                );
                    FileWork.reportWriteln(reportF,"\t    and1 = " + formalOutput(Word.and(stack.get(1).word,stack.get(2).word)));
                    FileWork.reportWriteln(reportF,"\t    and2 = " + formalOutput(Word.and(stack.get(1).not(),stack.get(3).word)));
                    FileWork.reportWriteln(reportF,"\t      or = " + formalOutput(F.word));

                    FileWork.reportWriteln(reportF);

                    FileWork.reportWriteln(reportF,"\tA = " + stack.get(0).getLong() + " = " + formalOutput(stack.get(0).word));
                    FileWork.reportWriteln(reportF,"\tF = " + F.getLong() + " = " + formalOutput(F.word));
                tempWord.word = bin(sumMod32(stack.get(0).getLong(),F.getLong()));
                    FileWork.reportWriteln(reportF,"\tA + F mod 2 ^32 = " + tempWord.getLong() + " = " + formalOutput(tempWord.word));

                    FileWork.reportWriteln(reportF);

                MWords.word = getMString(bits,j);
                    FileWork.reportWriteln(reportF,"\tM[" + j + "] = " + MWords.getLong() + " = " + formalOutput(MWords.word));
                tempWord.word = bin(sumMod32(tempWord.getLong(),MWords.getLong()));
                    FileWork.reportWriteln(reportF,"\tM[" + j + "] + p(2) mod 2 ^32 = " + tempWord.getLong() + " = " + formalOutput(tempWord.word));

                    FileWork.reportWriteln(reportF);

                    FileWork.reportWriteln(reportF,"\t" + formalOutput(tempWord.word) + " <<< " + Fs[j % 4]);
                tempWord.rotateLeft(Fs[j % 4]);
                    FileWork.reportWriteln(reportF,"\t" + formalOutput(tempWord.word));

                    FileWork.reportWriteln(reportF);

                stack.get(0).word = stack.get(3).word;
                stack.get(3).word = stack.get(2).word;
                stack.get(2).word = stack.get(1).word;
                stack.get(1).word = tempWord.word;

                ix = 0;
                for (Word w:stack) {
                 FileWork.reportWriteln(reportF,"stack(" + ix++ + ") = " + w.getLong() + " = " + formalOutput(w.word));
                }
                    FileWork.reportWriteln(reportF,"\n");
            }

            int[] Gs = {3,5,9,13};
            int[] Gk = {0,4,8,12,1,5,9,13,2,6,10,14,3,7,11,15};
            long Gconst = 0x5A827999;
            Word G = new Word();
            FileWork.reportWriteln(reportG,"Start stack:");

            ix = 0;

            for (Word w:stack) {
                FileWork.reportWriteln(reportG,"stack(" + ix++ + ") = " + w.getLong() + " = " + formalOutput(w.word));
            }
            for (int j = 0; j < 16; j++) {
                    FileWork.reportWriteln(reportG,"J = " + j);
                    FileWork.reportWriteln(reportG,"\t   B = " + formalOutput(stack.get(1).word));
                    FileWork.reportWriteln(reportG,"\t   C = " + formalOutput(stack.get(2).word));
                    FileWork.reportWriteln(reportG,"\tand1 = " + formalOutput(Word.and(stack.get(1).word,stack.get(2).word)));
                    FileWork.reportWriteln(reportG);
                    FileWork.reportWriteln(reportG,"\t   B = " + formalOutput(stack.get(1).word));
                    FileWork.reportWriteln(reportG,"\t   D = " + formalOutput(stack.get(3).word));
                    FileWork.reportWriteln(reportG,"\tand2 = " + formalOutput(Word.and(stack.get(1).word,stack.get(3).word)));
                    FileWork.reportWriteln(reportG);
                    FileWork.reportWriteln(reportG,"\t   C = " + formalOutput(stack.get(2).word));
                    FileWork.reportWriteln(reportG,"\t   D = " + formalOutput(stack.get(3).word));
                    FileWork.reportWriteln(reportG,"\tand3 = " + formalOutput(Word.and(stack.get(2).word,stack.get(3).word)));
                    FileWork.reportWriteln(reportG);
                G.word = Word.or(
                                    Word.and(stack.get(1).word,stack.get(2).word),
                                    Word.or(
                                                Word.and(stack.get(1).word,stack.get(3).word),
                                                Word.and(stack.get(2).word,stack.get(3).word)
                                            )
                                );
                    FileWork.reportWriteln(reportG,"\tand1 = " + formalOutput(Word.and(stack.get(1).word,stack.get(2).word)));
                    FileWork.reportWriteln(reportG,"\tand2 = " + formalOutput(Word.and(stack.get(1).word,stack.get(3).word)));
                    FileWork.reportWriteln(reportG,"\tand3 = " + formalOutput(Word.and(stack.get(2).word,stack.get(3).word)));
                    FileWork.reportWriteln(reportG,"\t  or = " + formalOutput(G.word));

                    FileWork.reportWriteln(reportG);

                    FileWork.reportWriteln(reportG,"\t   A = " + stack.get(0).getLong() + " = " + formalOutput(stack.get(0).word));
                    FileWork.reportWriteln(reportG,"\t   G = " + G.getLong() + " = " + formalOutput(G.word));
                tempWord.word = bin(sumMod32(stack.get(0).getLong(),G.getLong()));
                    FileWork.reportWriteln(reportG,"\tp(2) = A + F mod 2 ^32 = " + tempWord.getLong() + " = " + formalOutput(tempWord.word));

                    FileWork.reportWriteln(reportG);

                MWords.word = getMString(bits,Gk[j]);
                    FileWork.reportWriteln(reportG,"\tM[" + Gk[j] + "] = " + MWords.getLong() + " = " + formalOutput(MWords.word));
                tempWord.word = bin(sumMod32(tempWord.getLong(),MWords.getLong()));
                    FileWork.reportWriteln(reportG,"\tp(3) = M[" + Gk[j] + "] + p(2) mod 2 ^32 = " + tempWord.getLong() + " = " + formalOutput(tempWord.word));

                    FileWork.reportWriteln(reportG);

                    FileWork.reportWriteln(reportG,"\t       Gconst = " + Gconst);
                tempWord.word = bin(sumMod32(Gconst, tempWord.getLong()));
                    FileWork.reportWriteln(reportG,"\tp(3) + Gconst = " + tempWord.getLong() + " = " + formalOutput(tempWord.word));

                    FileWork.reportWriteln(reportG);

                    FileWork.reportWriteln(reportG,"\t" + formalOutput(tempWord.word) + " <<< " + Gs[j % 4]);
                tempWord.rotateLeft(Gs[j % 4]);
                    FileWork.reportWriteln(reportG,"\t" + formalOutput(tempWord.word));


                stack.get(0).word = stack.get(3).word;
                stack.get(3).word = stack.get(2).word;
                stack.get(2).word = stack.get(1).word;
                stack.get(1).word = tempWord.word;

                FileWork.reportWriteln(reportG);

                ix = 0;
                for (Word w:stack) {
                    FileWork.reportWriteln(reportG,"stack(" + ix++ + ") = " + w.getLong() + " = " + formalOutput(w.word));
                }
                FileWork.reportWriteln(reportG,"\n");
            }

            int[] Hs = {3,9,11,15};
            int[] Hk = {0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15};
            long Hconst = 0x6ED9EBA1;
            Word H = new Word();
            FileWork.reportWriteln(reportH,"Start stack:");

            ix = 0;

            for (Word w:stack) {
                FileWork.reportWriteln(reportH,"stack(" + ix++ + ") = " + w.getLong() + " = " + formalOutput(w.word));
            }
            for (int j = 0; j < 16; j++) {
                    FileWork.reportWriteln(reportH,"J = " + j);
                    FileWork.reportWriteln(reportH,"\t  B = " + formalOutput(stack.get(1).word));
                    FileWork.reportWriteln(reportH,"\t  C = " + formalOutput(stack.get(2).word));
                    FileWork.reportWriteln(reportH,"\t  D = " + formalOutput(stack.get(3).word));

                H.word = Word.xor(
                                    Word.xor(stack.get(1).word,stack.get(2).word),
                                    stack.get(3).word
                                );
                    FileWork.reportWriteln(reportH,"\txor = " + formalOutput(H.word));

                    FileWork.reportWriteln(reportH);

                    FileWork.reportWriteln(reportH,"\tA = " + stack.get(0).getLong() + " = " + formalOutput(stack.get(0).word));
                    FileWork.reportWriteln(reportH,"\tH = " + H.getLong() + " = " + formalOutput(H.word));
                tempWord.word = bin(sumMod32(stack.get(0).getLong(),H.getLong()));
                    FileWork.reportWriteln(reportH,"\tp(2) = A + H mod 2 ^32 = " + tempWord.getLong() + " = " + formalOutput(tempWord.word));

                    FileWork.reportWriteln(reportH);

                MWords.word = getMString(bits,Hk[j]);
                    FileWork.reportWriteln(reportH,"\tM[" + Hk[j] + "] = " + MWords.getLong() + " = " + formalOutput(MWords.word));
                tempWord.word = bin(sumMod32(tempWord.getLong(),MWords.getLong()));
                    FileWork.reportWriteln(reportH,"\tp(3) = M[" + Hk[j] + "] + p(2) mod 2 ^32 = " + tempWord.getLong() + " = " + formalOutput(tempWord.word));

                    FileWork.reportWriteln(reportH);

                    FileWork.reportWriteln(reportH,"\t       Hconst = " + Hconst);
                tempWord.word = bin(sumMod32(Hconst, tempWord.getLong()));
                    FileWork.reportWriteln(reportH,"\tp(3) + Hconst = " + tempWord.getLong() + " = " + formalOutput(tempWord.word));

                    FileWork.reportWriteln(reportH);


                    FileWork.reportWriteln(reportH,"\t" + formalOutput(tempWord.word) + " <<< " + Hs[j % 4]);
                tempWord.rotateLeft(Hs[j % 4]);
                    FileWork.reportWriteln(reportH,"\t" + formalOutput(tempWord.word));

                    FileWork.reportWriteln(reportH);

                stack.get(0).word = stack.get(3).word;
                stack.get(3).word = stack.get(2).word;
                stack.get(2).word = stack.get(1).word;
                stack.get(1).word = tempWord.word;

                ix = 0;
                for (Word w:stack) {
                    FileWork.reportWriteln(reportH,"stack(" + ix++ + ") = " + w.getLong() + " = " + formalOutput(w.word));
                }
                FileWork.reportWriteln(reportH,"\n");

            }
            stack.get(0).word = bin(sumMod32(stack.get(0).getLong(),AA.getLong()));
            stack.get(1).word = bin(sumMod32(stack.get(1).getLong(),BB.getLong()));
            stack.get(2).word = bin(sumMod32(stack.get(2).getLong(),CC.getLong()));
            stack.get(3).word = bin(sumMod32(stack.get(3).getLong(),DD.getLong()));
        }

        for (Word word:stack) {
            FileWork.reportWrite(reportFinal,formalOutput(word.word) + "");
        }
        FileWork.reportWriteln(reportFinal,"\nhex :\n");
        int temp;
        for (Word word:stack) {
            for (int i = 7; i >= 0; i--) {
                temp = 0;
                for (int j = 3; j >= 0; j--) {

                    temp += word.word.get(i * 4 + j) * (int)Math.pow(2,3 - j);

                }
                FileWork.reportWrite(reportFinal,Integer.toHexString(temp) + "");
            }
        }

    }
}
