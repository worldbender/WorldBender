public class Player {

    private int x=500;
    private int y=500;
    private int hp=10;

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setPosition(String content){
        char letter = content.charAt(0);
        if(letter=='A'){
            this.x-=5;
        }
        else if(letter=='D'){
            this.x+=5;
        }
        else if(letter=='W'){
            this.y+=5;
        }
        else if(letter=='S'){
            this.y-=5;
        }
    }
}
