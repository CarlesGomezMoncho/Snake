package com.stiwi.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Carles on 19/10/2016.
 */

public class Serp {

    private CapSerp cap;
    private CuaSerp cua;
    private ArrayList<PartSerp> cos; //distints segments del cos de la serp
    private ArrayList<Integer> direccions; //guardem els canvis de direccio pendents
    private int direccio;   //1,2,3,4 -> dreta, baix, esquerra, dalt, esta es la direcció actual
    private int velocitat; //velocitat a la que es mou la serp
    private int afegirPart; //per saber si hem de afegir alguna part nova a la ser

    public Serp()
    {
        //constructor buit per a guardar en json
        //direcció i velocitats inicials
        direccio = 1;
        velocitat = 1;

        //afegim cap de la serp
        cap = new CapSerp(0, 0);
        cap.setDireccio(direccio);

        //inicialitzem array de direccions
        direccions = new ArrayList<Integer>();

        //afegim cos de la serp amb segments inicials
        cos = new ArrayList<PartSerp>();
        cos.add(new PartSerp(cap.getX(), cap.getY()));
        cos.add(new PartSerp(cap.getX(), cap.getY()));
        cos.add(new PartSerp(cap.getX(), cap.getY()));
        cos.add(new PartSerp(cap.getX(), cap.getY()));
        cos.add(new PartSerp(cap.getX(), cap.getY()));

        //afegim la cua
        cua = new CuaSerp(0,0);

        //no afegim cap part nova a la serp de inici
        afegirPart = 0;

    }

    public Serp(int x, int y)
    {

        //direcció i velocitats inicials
        direccio = 1;
        velocitat = 1;

        //afegim cap de la serp
        cap = new CapSerp(x, y);
        cap.setDireccio(direccio);

        //inicialitzem array de direccions
        direccions = new ArrayList<Integer>();

        //afegim cos de la serp amb segments inicials
        cos = new ArrayList<PartSerp>();
        cos.add(new PartSerp(cap.getX(), cap.getY()));
        cos.add(new PartSerp(cap.getX(), cap.getY()));
        cos.add(new PartSerp(cap.getX(), cap.getY()));
        cos.add(new PartSerp(cap.getX(), cap.getY()));
        cos.add(new PartSerp(cap.getX(), cap.getY()));

        //afegim la cua
        cua = new CuaSerp(0,0);

        //no afegim cap part nova a la serp de inici
        afegirPart = 0;

    }

    public int getX()
    {
        return cap.getX();
    }

    public int getY()
    {
        return cap.getY();
    }

    public void setX(int x) { cap.setX(x); }

    public void setY(int y)
    {
        cap.setY(y);
    }

    public int getVelocitat()
    {
        return velocitat;
    }

    public void setVelocitat(int velocitat)
    {
        this.velocitat = velocitat;
    }

    public int getDireccio()
    {
        return direccio;
    }

    //canviem la direcció de la serp
    public void addDireccio(int direccio)
    {
        direccions.add(direccio);
    }

    //moguem la serp depenent de la direcció
    public void mou()
    {

        //de darrere cap a avant moguem la serp (s
        for ( int i = cos.size()-1  ; i>=0; i--) {

            //agafem un fragment del cos
            PartSerp part = cos.get(i);

            //si i es l'ultim

            //agafem el anterior
            PartSerp partAnterior;
            //si i es 0, el cap es el anterior, sinó sera el del array[i-1]
            if (i == 0)
                partAnterior = cap;
            else
                partAnterior = cos.get(i-1);

            //si encara no hi ha direcció asignada, es que es la inicial i no es mourà
            // fins que deixe de colisionar amb l'anterior, este estat només es dona al
            //crear una nova part del cos, i està s'inicialitza exactament damunt de
            //l'anterior última part del cos
            if (part.getDireccio() == 0) {
                //si ja no colisiona en el anterior, es que ja podem començar a moure esta part
                if (!part.colisiona(partAnterior)) {
                    part.setDireccio(partAnterior.getDireccio());  //asignem la direcció del anterior
                }
            }else {

                //Si la direcció del anterior es diferent a la actual i encara no ho sabiem
                if (!part.isDireccioDiferent() && part.getDireccio() != partAnterior.getDireccio()) {
                    //guardem les coordenades del anterior i la nova direcció per a quan
                    //ens toque actualitzar la propia
                    part.setXant(partAnterior.getX());
                    part.setYant(partAnterior.getY());
                    part.setDireccioAnt(partAnterior.getDireccio());
                    part.setDireccioDiferent(true);
                }

                //si la direcció es diferent i ja hem aplegat e les coordenades del anterior
                //hem de canviar la direcció
                if (part.isDireccioDiferent())
                    switch (part.getDireccio())
                    {
                        //comprovem depenent de la direcció en la que anem la coordenada de l'eix, l'altra dona igual
                        case 1:
                            if (part.getX()>=part.getXant())
                            {
                                part.setDireccio(part.getDireccioAnt());
                                part.setDireccioDiferent(false);
                            }
                            break;
                        case 2:
                            if (part.getY()<=part.getYant())
                            {
                                part.setDireccio(part.getDireccioAnt());
                                part.setDireccioDiferent(false);
                            }
                            break;
                        case 3:
                            if (part.getX()<=part.getXant())
                            {
                                part.setDireccio(part.getDireccioAnt());
                                part.setDireccioDiferent(false);
                            }
                            break;
                        case 4:
                            if (part.getY()>=part.getYant())
                            {
                                part.setDireccio(part.getDireccioAnt());
                                part.setDireccioDiferent(false);
                            }
                            break;
                    }
            }

            //finalment moguem la part si no es 0
            switch (part.getDireccio()) {
                case 1:
                    part.setX(part.getX() + velocitat);
                    break;
                case 2:
                    part.setY(part.getY() - velocitat);
                    break;
                case 3:
                    part.setX(part.getX() - velocitat);
                    break;
                case 4:
                    part.setY(part.getY() + velocitat);
                    break;
            }
        }

        //moguem el cap i ajustem la rotació del sprite del cap
        switch (cap.getDireccio())
        {
            case 1: setX(cap.getX()+velocitat);
                cap.setRotation(180);
                break;
            case 2: setY(cap.getY()-velocitat);
                cap.setRotation(90);
                break;
            case 3: setX(cap.getX()-velocitat);
                cap.setRotation(0);
                break;
            case 4: setY(cap.getY()+velocitat);
                cap.setRotation(-90);
                break;
        }

        //si la direcció del ultim no es diferent, afegix part si cal afegir
        if (afegirPart > 0 && !cos.get(cos.size()-1).isDireccioDiferent())
            addPart();


        //per evitar la rapida successió de canvis de direcció ens esperem a fer el canvi
        // quan ens em desplaçat el ample o el alt i l'agafem de un array de
        // les direccions clicades si hi han canvis de direccions
        if(!direccions.isEmpty())
        {
            //si el cap no colisiona amb el cos es que ja podem canviar de direcció,
            // si està colisionant, es que encara no podem canviar a una nova direcció
            if (!cap.colisiona(cos.get(0)))
            {
                direccio = direccions.get(0);
                cap.setDireccio(direccio);    //asignem la nova direccio
                direccions.remove(0);   //la borrem de canvis de direccions pendents
            }
        }

        //comprovem que les posicions de cadascuna de les parts de la serp estan on toca
        //pasa molt poc, pero alguna volta no se per que però es posa una nova part mal
        for (int i = 0; i< cos.size()-1; i++)
        {
            PartSerp actual, posterior;
            actual = cos.get(i);
            posterior = cos.get(i+1);

            //si estan anant en la mateixa direcció i ja està la posterior en moviment
            if (actual.getDireccio() == posterior.getDireccio())
            {
                switch (actual.getDireccio())
                {
                    case 1:
                        if (actual.getX()-actual.getWidth() != posterior.getX())
                            posterior.setX(actual.getX()-actual.getWidth());
                        if (actual.getY() != posterior.getY())
                            posterior.setY(actual.getY());
                        break;
                    case 2:
                        if (actual.getX() != posterior.getX())
                            posterior.setX(actual.getX());
                        if (actual.getY()+actual.getHeight() != posterior.getY())
                            posterior.setY(actual.getY());
                        break;
                    case 3:
                        if (actual.getX()+actual.getWidth() != posterior.getX())
                            posterior.setX(actual.getX()-actual.getWidth());
                        if (actual.getY() != posterior.getY())
                            posterior.setY(actual.getY());
                        break;
                    case 4:
                        if (actual.getX() != posterior.getX())
                            posterior.setX(actual.getX());
                        if (actual.getY()-actual.getHeight() != posterior.getY())
                            posterior.setY(actual.getY());
                        break;
                }
            }
        }


    }

    //renderitzem cadascuna de les parts de la serp
    public void render()
    {
        cap.render();
        for (PartSerp part: cos) {
            part.render();
        }

    }


    public boolean colisiona (Primitiva primitiva)
    {
        //comprovem primer el cap
        if (cap.colisiona(primitiva))
            return true;

        //si el cap no colisiona, pasem a el cos
        return colisionaCos(primitiva);
    }

    public boolean colisionaCos(Primitiva primitiva)
    {
        //recorrem tot el array de parts excepte el primer (ja que el cap sempre xoca amb el primer en un canvi de direcció), per si colisiona
        //for (PartSerp p: cos)
        for (int i = 1; i < cos.size(); i++)
        {
            PartSerp p = cos.get(i);
            if (p.colisiona(primitiva))
                return true;
        }

        return false;
    }

    //per detectar si el cap colisiona amb el cos
    public boolean colisionaAmbEllaMateixa()
    {
        //si encara no ha començat a moures el cos, torna fals, per que encara no s'haurà desenrollat la serp
        if (cos.get(0).getDireccio() == 0)
            return false;

        return colisionaCos(cap);
    }

    public void incrementaParts()
    {
        afegirPart++;
    }

    public void addPart()
    {
        afegirPart--; //restem 1 per si hi han parts pendents

        int ultim = cos.size()-1;   //agafem la última part actual
        PartSerp ultimaPart = cos.get(ultim);

        //creem una nova part amb les mateixes característiques del ultim
        PartSerp nou = new PartSerp(ultimaPart.getX(), ultimaPart.getY());

        //copiem les característiques finals del ultim si s'està moguent quan s'afegeix


        //nou.setXant(ultimaPart.getXant());
        //nou.setYant(ultimaPart.getYant());


        //if (ultimaPart.isDireccioDiferent()) {
        //    nou.setDireccioAnt(ultimaPart.getDireccioAnt());  //la direccio actual ha de ser 0
        //}
        //nou.setColor(new com.badlogic.gdx.graphics.Color(1f, 0.5f, 0.5f, 1));

        //afegim la nova part
        cos.add(nou);

    }

    public CapSerp getCap() {
        return cap;
    }

    public void setCap(CapSerp cap) {
        this.cap = cap;
    }

    public ArrayList<PartSerp> getCos() {
        return cos;
    }

    public void setCos(ArrayList<PartSerp> cos) {
        this.cos = cos;
    }

    public ArrayList<Integer> getDireccions() {
        return direccions;
    }

    public void setDireccions(ArrayList<Integer> direccions) {
        this.direccions = direccions;
    }

    public void setDireccio(int direccio) {
        this.direccio = direccio;
    }

    public int getAfegirPart() {
        return afegirPart;
    }

    public void setAfegirPart(int afegirPart) {
        this.afegirPart = afegirPart;
    }

    public void save()
    {
        FileHandle save = Gdx.files.local("saveFile");
        Json json = new Json();

        save.writeString(json.prettyPrint(cap), false);
        save.writeString(json.prettyPrint(cos), true);

        //save.writeString("Text afegit", false);
    }

    public void load()
    {
        FileHandle saveFile = Gdx.files.local("saveFile");
        Json json = new Json();

        cap = json.fromJson(CapSerp.class, saveFile.readString());
        cos = json.fromJson(ArrayList.class, saveFile.readString());

    }

}
