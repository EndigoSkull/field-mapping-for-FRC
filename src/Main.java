public class Main {
    public static void main(String[] args) {
        double startTime = System.nanoTime();

        FieldMapTest baseMap;
        FieldMapWithInputTest inputMap = new FieldMapWithInputTest(50, 20);

        int randomLineNum = 0;
        int randomCircleNum = 0;
        boolean randomFillCircle = false;
        boolean fillCircle = true;
        int randomPolygonNum = 1;
        int polygonVertNum = 4;
        boolean fillPolygon = true;

        for(int i = 0; i < randomLineNum; i++) {
            inputMap.drawLine(Math.random() * inputMap.getMapX(), Math.random() * inputMap.getMapY(), Math.random() * inputMap.getMapX(), Math.random() * inputMap.getMapY());
        }

        for(int i = 0; i < randomCircleNum; i++) {
            double centerX = Math.random() * inputMap.getMapX();
            double centerY = Math.random() * inputMap.getMapY();
            double radius = Math.random()*((Math.min(inputMap.getMapX(), inputMap.getMapY()))/2.0);

            if(randomFillCircle)
                inputMap.drawCircle(centerX, centerY, radius, Math.random() > 0.5);
            else
                inputMap.drawCircle(centerX, centerY, radius, fillCircle);
        }

        for(int i = 0; i < randomPolygonNum; i++) {
            double[] verticesX = new double[polygonVertNum];
            double[] verticesY = new double[polygonVertNum];

            for(int j = 0; j < polygonVertNum; j++)
                verticesX[j] = Math.random() * inputMap.getMapX();
            for(int j = 0; j < polygonVertNum; j++)
                verticesY[j] = Math.random() * inputMap.getMapY();

            inputMap.drawPolygon(verticesX, verticesY, fillPolygon);
        }

        double stopTime = System.nanoTime();

        System.out.println("Combined Map:");
        System.out.println(inputMap);
        System.out.println("Base Map:");
        System.out.println(inputMap.getBaseMap());
        System.out.println("Line Input Map:");
        System.out.println(inputMap.getLineInputMap());
        System.out.println("Circle Input Map:");
        System.out.println(inputMap.getCircleInputMap());
        System.out.println("Circle Drawn Map");
        System.out.println(inputMap.getCircleMap(false, true));
        System.out.println();

        System.out.println((stopTime-startTime)/1000000000);
        System.out.println();

        System.out.println("Line X inputs: "+inputMap.getInputLineX());
        System.out.println("Line Y inputs: "+inputMap.getInputLineY());
        System.out.println("Line counts: "+inputMap.getInputLineCount());
        System.out.println();

        System.out.println("Circle X inputs: "+inputMap.getInputCircleX());
        System.out.println("Circle Y inputs: "+inputMap.getInputCircleY());
        System.out.println("Circle radius inputs: "+inputMap.getInputCircleRadius());
        System.out.println("Circle fill inputs: "+inputMap.getInputCircleFilled());
        System.out.println("Circle counts: "+inputMap.getInputCircleCount());
        System.out.println();

        System.out.println("Polygon X inputs: "+inputMap.getInputPolygonX());
        System.out.println("Polygon Y inputs: "+inputMap.getInputPolygonY());
        System.out.println();
    }
}