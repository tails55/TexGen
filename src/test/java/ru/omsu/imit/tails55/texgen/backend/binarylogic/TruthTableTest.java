package ru.omsu.imit.tails55.texgen.backend.binarylogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TruthTableTest {

    @Test
    void not() {
        TruthTable a=new TruthTable(3,0);
        TruthTable notA= TruthTable.not(a);
        assertArrayEquals(notA.getValues(), new boolean[]{true, false, true, false, true, false, true, false});
    }

    @Test
    void and() {
        TruthTable a=new TruthTable(3,0);
        TruthTable b=new TruthTable(3,1);
        TruthTable aAndB= TruthTable.and(a,b);
        assertArrayEquals(aAndB.getValues(), new boolean[]{false,false,false,true,false,false,false,true});
    }

    @Test
    void or() {
        TruthTable a=new TruthTable(3,0);
        TruthTable b=new TruthTable(3,1);
        TruthTable aOrB= TruthTable.or(a,b);
        assertArrayEquals(aOrB.getValues(), new boolean[]{false,true,true,true,false,true,true,true});
    }

    @Test
    void xor() {
        TruthTable a=new TruthTable(3,0);
        TruthTable b=new TruthTable(3,1);
        TruthTable aOrB= TruthTable.xor(a,b);
        assertArrayEquals(aOrB.getValues(), new boolean[]{false,true,true,false,false,true,true,false});
    }

    @Test
    void complicatedTest() {
        TruthTable a=new TruthTable(3,0);
        TruthTable b=new TruthTable(3,1);
        TruthTable c=new TruthTable(3,2);
        TruthTable aOrB= TruthTable.or(a,b);
        TruthTable notC= TruthTable.not(c);
        TruthTable aOrBAndNotC= TruthTable.and(aOrB,notC);
        assertArrayEquals(aOrBAndNotC.getValues(), new boolean[]{false,true,true,true,false,false,false,false});
    }
}