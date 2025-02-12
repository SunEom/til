package FinalExample.Problem4;

public class MyColorPoint extends MyPoint{
   private String color;

   public MyColorPoint(int x, int y, String color){
      super(x,y);
      this.color = color;
   }

   @Override
   protected void move(int x, int y) {
      this.x = x;
      this.y = y;
   }

   @Override
   protected void reverse() {
      int temp = this.x;
      this.x = this.y;
      this.y = temp;
   }

   @Override
   public void show(){
      System.out.println(x+","+y+","+color);
   }
}
