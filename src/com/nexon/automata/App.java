package com.nexon.automata;

import com.nexon.automata.pair.Automata;

/**
 * Created by chan8 on 2017-01-11.
 */
public class App {

    public static void main(String[] args) {
        new App();
    }

    public App() {
        Automata automata = new Automata();
        automata.initialize();
        System.out.println(automata.analyze("A3A4E1"));
        System.out.println(automata.analyze("E4DE1BC4"));
        System.out.println(automata.analyze("E1DAC1"));
        System.out.println(automata.analyze("CC1B5E"));
        System.out.println(automata.analyze("E1DAA//E1DAA4B1"));
        System.out.println(automata.analyze("A31E"));
        System.out.println(automata.analyze("E1CC1"));
    }
}
