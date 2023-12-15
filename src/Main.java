public class Main {
    public static void main(String[] args) {
        double startTime = System.nanoTime();

        FieldMapWithInputTest baseMap = new FieldMapWithInputTest(100, 100);

        int randomLineNum = 3;
        int randomCircleNum = 3;
        boolean fillCircle = true;

        for(int i = 0; i < randomLineNum; i++) {
            baseMap.drawLine(Math.random() * baseMap.getMapX(), Math.random() * baseMap.getMapY(), Math.random() * baseMap.getMapX(), Math.random() * baseMap.getMapY());
        }

        for(int i = 0; i < randomCircleNum; i++) {
            double centerX = Math.random() * baseMap.getMapX();
            double centerY = Math.random() * baseMap.getMapY();
            double radius = Math.random()*((Math.min(baseMap.getMapX(), baseMap.getMapY()))/2);

            if(fillCircle)
                baseMap.drawCircleFilled(centerX, centerY, radius);
            else
                baseMap.drawCircleEmpty(centerX, centerY, radius);
        }

        double stopTime = System.nanoTime();

        System.out.println(baseMap);
        System.out.println((stopTime-startTime)/1000000000);
    }
}