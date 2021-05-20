package city;

import random.MersenneTwisterFast;

public class Mosbach {

    String[][] cityarea = new String[1000][1000];

    public Mosbach() {

        for (int i = 0; i < 1000 ; i++) {
            for (int j = 0; j < 1000 ; j++) {
                this.cityarea[i][j] = "_";
            }
        }

        this.cityarea[250][250] = "D";
        this.cityarea[750][250] = "D";
        this.cityarea[250][750] = "D";
        this.cityarea[750][750] = "D";
        this.cityarea[500][500] = "E";

        MersenneTwisterFast merTwi = new MersenneTwisterFast();
        int x;
        int y;
        for (int i = 0; i < 32500; i++) {
            x = merTwi.nextInt(0,999);
            y = merTwi.nextInt(0,999);

            if (this.cityarea[x][y].equals("_")){
                this.cityarea[x][y] = "R";
            } else i--;

        }

        for (int i = 0; i < 2500; i++) {
            x = merTwi.nextInt(0,999);
            y = merTwi.nextInt(0,999);

            if (this.cityarea[x][y].equals("_")){
                this.cityarea[x][y] = "S";
            } else i--;

        }

        for (int i = 0; i < 1000; i++) {
            x = merTwi.nextInt(1,997);
            y = merTwi.nextInt(1,998);

            if (this.cityarea[x][y].equals("_") &&
                    this.cityarea[x-1][y+1].equals("_") &&
                    this.cityarea[x-1][y].equals("_") &&
                    this.cityarea[x-1][y-1].equals("_") &&
                    this.cityarea[x][y+1].equals("_") &&
                    this.cityarea[x][y-1].equals("_") &&
                    this.cityarea[x+1][y+1].equals("_") &&
                    this.cityarea[x+1][y].equals("_") &&
                    this.cityarea[x+1][y-1].equals("_") &&
                    this.cityarea[x+2][y+1].equals("_") &&
                    this.cityarea[x+2][y].equals("_") &&
                    this.cityarea[x+2][y-1].equals("_")
            ){
                this.cityarea[x][y] = "H";
                this.cityarea[x+1][y] = "H";
            } else i--;

        }

    }

    public String[][] getCityarea() {
        return cityarea;
    }
}
