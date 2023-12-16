public class Main {
    private static final int mapX = 50;
    private static final int mapY = 20;

    private static final int randomLineNum = 0;
    private static final int randomCircleNum = 0;
    private static final boolean randomFillCircle = false;
    private static final boolean fillCircle = true;
    private static final int randomPolygonNum = 1;
    private static final int polygonVertNum = 10;
    private static final boolean fillPolygon = true;

    public static void main(String[] args) {
        FieldMapTest baseScaleMap = new FieldMapTest(1654, 802);
        FieldMapTest toScaleMap = new FieldMapTest(mapX, mapY);

        FieldMapWithInputTest inputBaseScaleMap = new FieldMapWithInputTest(1654, 802);
        FieldMapWithInputTest inputToScaleMap = new FieldMapWithInputTest(mapX, mapY);


        double baseScaleMapTime = runTests(baseScaleMap);
        double toScaleMapTime = runTests(toScaleMap);

        double inputBaseScaleMapTime = runTests(inputBaseScaleMap);
        double inputToScaleMapTime = runTests(inputToScaleMap);

        printData(inputToScaleMap, inputToScaleMapTime);

        System.out.println("baseScaleMapTime: "+baseScaleMapTime);
        System.out.println("toScaleMapTime: "+toScaleMapTime);
        System.out.println("Ratio of baseScaleMapTime to toScaleMapTime: "+(baseScaleMapTime/toScaleMapTime));
        System.out.println("Ratio of baseScaleMap area to toScaleMap area: "+((baseScaleMap.getMapX()*baseScaleMap.getMapY())/(toScaleMap.getMapX()*toScaleMap.getMapY())));
        System.out.println("Ratio of time ratio to area ratio: "+((baseScaleMapTime/toScaleMapTime)/((baseScaleMap.getMapX()*baseScaleMap.getMapY())/(toScaleMap.getMapX()*toScaleMap.getMapY()))));
        System.out.println();
        System.out.println("inputBaseScaleMapTime: "+inputBaseScaleMapTime);
        System.out.println("inputToScaleMapTime: "+inputToScaleMapTime);
        System.out.println("Ratio of inputBaseScaleMapTime to inputToScaleMapTime: "+(inputBaseScaleMapTime/inputToScaleMapTime));
        System.out.println("Ratio of inputBaseScaleMap area to inputToScaleMap area: "+((inputBaseScaleMap.getMapX()*inputBaseScaleMap.getMapY())/(inputToScaleMap.getMapX()*inputToScaleMap.getMapY())));
        System.out.println("Ratio of time ratio to area ratio: "+((inputBaseScaleMapTime/inputToScaleMapTime)/((inputBaseScaleMap.getMapX()*inputBaseScaleMap.getMapY())/(inputToScaleMap.getMapX()*inputToScaleMap.getMapY()))));
    }
    public static double runTests(FieldMapTest field){
         double startTime = System.nanoTime();

        for(int i = 0; i < randomLineNum; i++) {
            field.drawLine(Math.random() * field.getMapX(), Math.random() * field.getMapY(), Math.random() * field.getMapX(), Math.random() * field.getMapY());
        }

        for(int i = 0; i < randomCircleNum; i++) {
            double centerX = Math.random() * field.getMapX();
            double centerY = Math.random() * field.getMapY();
            double radius = Math.random()*((Math.min(field.getMapX(), field.getMapY()))/2.0);

            if(randomFillCircle)
                field.drawCircle(centerX, centerY, radius, Math.random() > 0.5);
            else
                field.drawCircle(centerX, centerY, radius, fillCircle);
        }

        for(int i = 0; i < randomPolygonNum; i++) {
            double[] verticesX = new double[polygonVertNum];
            double[] verticesY = new double[polygonVertNum];

            for(int j = 0; j < polygonVertNum; j++)
                verticesX[j] = Math.random() * field.getMapX();
            for(int j = 0; j < polygonVertNum; j++)
                verticesY[j] = Math.random() * field.getMapY();

            field.drawPolygon(verticesX, verticesY, fillPolygon);
        }

        double stopTime = System.nanoTime();

        return (stopTime-startTime)/1000000;
    }

    public static void printData(FieldMapWithInputTest field, double time){
        System.out.println("Combined Map:");
        System.out.println(field);
        System.out.println("Base Map:");
        System.out.println(field.getBaseMap());
        System.out.println("Line Input Map:");
        System.out.println(field.getLineInputMap());
        System.out.println("Line Map:");
        System.out.println(field.getLineMap());
        System.out.println("Circle Input Map:");
        System.out.println(field.getCircleInputMap());
        System.out.println("Circle Map");
        System.out.println(field.getCircleMap(true, true));
        System.out.println("Polygon Input Map:");
        System.out.println(field.getPolygonInputMap());
        System.out.println("Polygon Map:");
        System.out.println(field.getPolygonMap());
        System.out.println();

        System.out.println("Time Taken: "+time);
        System.out.println();

        System.out.println("Line X inputs: "+field.getInputLineX());
        System.out.println("Line Y inputs: "+field.getInputLineY());
        System.out.println("Line counts: "+field.getInputLineCount());
        System.out.println();

        System.out.println("Circle X inputs: "+field.getInputCircleX());
        System.out.println("Circle Y inputs: "+field.getInputCircleY());
        System.out.println("Circle radius inputs: "+field.getInputCircleRadius());
        System.out.println("Circle fill inputs: "+field.getInputCircleFilled());
        System.out.println("Circle counts: "+field.getInputCircleCount());
        System.out.println();

        System.out.println("Polygon X inputs: "+field.getInputPolygonX());
        System.out.println("Polygon Y inputs: "+field.getInputPolygonY());
        System.out.println("Polygon fill inputs: "+field.getInputPolygonFilled());
        System.out.println("Polygon counts: "+field.getInputPolygonCount());
        System.out.println();
    }
}