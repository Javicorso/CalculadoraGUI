/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unbosque.swii.caluladora;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Huber
 */
@ManagedBean
@SessionScoped
public class Calculos implements Serializable{
    
    

    private String sentence;
    private boolean flag;
    private int operation;
    private SortedMap<String, Integer> secuence;

    public Calculos() {
        this.secuence = new TreeMap<>();
        sentence = "";
        operation = 100;
    }

    public String getSentencia() {
        return sentence;
    }

    public void setSentencia(String sentencia) {
        this.sentence = sentencia;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void buildSentence(String value) {
        if (flag) {
            sentence = "";
            flag = false;
        }
        sentence = sentence + value;
    }

    public String clearSentence() {
        sentence = "";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("Calculos");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Calculos", new Calculos());
        return "calculadoragui?faces-redirect=true";
    }

    public void calculations() {
        if (sentence.equals("")) {
            secuence.put(index()+"EQU", 0);
        } else if (secuence.isEmpty()) {
            secuence.put(index()+"EQU", Integer.valueOf(sentence));
        } else {
            resolveLastOperation();
        }
        flag = true;
        sentence = secuence.get(secuence.lastKey()).toString();
    }

    public void add() {
        if (sentence.equals("")) {
            secuence.put(index()+"ADD", 0);
        } else if (secuence.isEmpty()) {
            secuence.put(index()+"ADD", Integer.valueOf(sentence));
        } else {
            resolveLastOperation();
            secuence.put(index()+"ADD", Integer.valueOf(sentence));
        }
        flag = true;
        sentence = secuence.get(secuence.lastKey()).toString();
    }

    public void subtract() {
        if (sentence.equals("")) {
            secuence.put(index()+"SUB", 0);
        } else if (secuence.isEmpty()) {
            secuence.put(index()+"SUB", Integer.valueOf(sentence));
        } else {
            resolveLastOperation();
            secuence.put(index()+"SUB", Integer.valueOf(sentence));
        }
        flag = true;
        sentence = secuence.get(secuence.lastKey()).toString();
    }

    public void multiply() {
        if (sentence.equals("")) {
            secuence.put(index()+"MUL", 1);
        } else if (secuence.isEmpty()) {
            secuence.put(index()+"MUL", Integer.valueOf(sentence));
        } else {
            resolveLastOperation();
            secuence.put(index()+"MUL", Integer.valueOf(sentence));
        }
        flag = true;
        sentence = secuence.get(secuence.lastKey()).toString();
    }

    public void divide() {
        if (sentence.equals(index()+"")) {
            secuence.put(index()+"DIV", 1);
        } else if (secuence.isEmpty()) {
            secuence.put(index()+"DIV", Integer.valueOf(sentence));
        } else {
            resolveLastOperation();
            secuence.put(index()+"DIV", Integer.valueOf(sentence));
        }
        flag = true;
        sentence = secuence.get(secuence.lastKey()).toString();

    }
    
    public void resolveLastOperation() {
        String op = secuence.lastKey();
        switch (op.substring(3, 6)) {
                case ("ADD"):
                    secuence.put(index()+"ADD", Integer.valueOf(sentence) + secuence.get(secuence.lastKey()));
                    break;
                case ("SUB"):
                    secuence.put(index()+"SUB", secuence.get(secuence.lastKey()) - Integer.valueOf(sentence));
                    break;
                case ("MUL"):
                    secuence.put(index()+"MUL", Integer.valueOf(sentence) * secuence.get(secuence.lastKey()));
                    break;
                case ("DIV"):
                    if (!sentence.equals("0")) {
                        secuence.put(index()+"DIV", secuence.get(secuence.lastKey()) / Integer.valueOf(sentence));
                    } else {
                        sentence = "ERROR";
                    }
                    break;
            }
        sentence = secuence.get(secuence.lastKey()).toString();
    }

    public int index(){
        operation = operation + 1;
        return operation;
    }
}
