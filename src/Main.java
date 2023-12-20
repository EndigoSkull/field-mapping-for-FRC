public class Main {
    private static final int mapX = 50;
    private static final int mapY = 20;

    public static final boolean randomExpansionWidthFromZero = true;
    private static final double expansionWidth = 10;
    private static final double expansionWidthIncrement = 5;

    private static final int randomLineNum = 2;

    private static final int randomCircleNum = 1;
    private static final boolean randomFillCircle = false;
    private static final boolean randomQuadrants = true;
    private static final boolean fillCircleQuadrants = false;

    private static final int randomPolygonNum = 1;
    private static final int polygonVertNum = 3;
    private static final boolean fillPolygon = true;

    private static final boolean runCustom = true;

    public static void main(String[] args) {
        FieldMapTest fullScaleMap = new FieldMapTest(1654, 802);
        FieldMapTest inputScaleMap = new FieldMapTest(mapX, mapY);

        FieldMapWithInputTest inputFullScaleMap = new FieldMapWithInputTest(1654, 802);
        FieldMapWithInputTest inputsInputScaleMap = new FieldMapWithInputTest(mapX, mapY);

        inputScaleMap.drawPolygon(new int[]{5, 11, 11, 1}, new int[]{5, 5, 11, 18}, true);
        System.out.println(inputScaleMap);
        int[] perp = inputScaleMap.getPerpPixel(0, 4, 4, 12, 12);
        System.out.println(perp[0]+" "+perp[1]);

        if(!runCustom){
            double baseScaleMapTime = runTests(fullScaleMap);
            double toScaleMapTime = runTests(inputScaleMap);

            double inputBaseScaleMapTime = runTests(inputFullScaleMap);
            double inputToScaleMapTime = runTests(inputsInputScaleMap);

//        double centerX = Math.random() * inputsInputScaleMap.getMapX();
//        double centerY = Math.random() * inputsInputScaleMap.getMapY();
//        double radius = Math.random()*((Math.min(inputsInputScaleMap.getMapX(), inputsInputScaleMap.getMapY()))/2.0);
//
//        boolean quadrant1 = Math.random() > 0.5;
//        boolean quadrant2 = Math.random() > 0.5;
//        boolean quadrant3 = Math.random() > 0.5;
//        boolean quadrant4 = Math.random() > 0.5;
//
//        //inputsInputScaleMap.drawCircleQuadrant(centerX, centerY, quadrant1, quadrant2, quadrant3, quadrant4, radius, fillCircle);
//        inputScaleMap.drawCircleQuadrant(centerX, centerY, quadrant1, quadrant2, quadrant3, quadrant4, radius, fillCircleQuadrants);
//
//        System.out.println(quadrant1);
//        System.out.println(quadrant2);
//        System.out.println(quadrant3);
//        System.out.println(quadrant4);
//        System.out.println("("+centerX+", "+centerY+")");
//        System.out.println(radius);
//
//        System.out.println(inputScaleMap);
            printData(inputsInputScaleMap, inputToScaleMapTime);

            System.out.println("baseScaleMapTime: " + baseScaleMapTime);
            System.out.println("toScaleMapTime: " + toScaleMapTime);
            System.out.println("Ratio of baseScaleMapTime to toScaleMapTime: " + (baseScaleMapTime / toScaleMapTime));
            System.out.println("Ratio of fullScaleMap area to inputScaleMap area: " + ((fullScaleMap.getMapX() * fullScaleMap.getMapY()) / (inputScaleMap.getMapX() * inputScaleMap.getMapY())));
            System.out.println("Ratio of time ratio to area ratio: " + ((baseScaleMapTime / toScaleMapTime) / ((fullScaleMap.getMapX() * fullScaleMap.getMapY()) / (inputScaleMap.getMapX() * inputScaleMap.getMapY()))));
            System.out.println();
            System.out.println("inputBaseScaleMapTime: " + inputBaseScaleMapTime);
            System.out.println("inputToScaleMapTime: " + inputToScaleMapTime);
            System.out.println("Ratio of inputBaseScaleMapTime to inputToScaleMapTime: " + (inputBaseScaleMapTime / inputToScaleMapTime));
            System.out.println("Ratio of inputFullScaleMap area to inputsInputScaleMap area: " + ((inputFullScaleMap.getMapX() * inputFullScaleMap.getMapY()) / (inputsInputScaleMap.getMapX() * inputsInputScaleMap.getMapY())));
            System.out.println("Ratio of time ratio to area ratio: " + ((inputBaseScaleMapTime / inputToScaleMapTime) / ((inputFullScaleMap.getMapX() * inputFullScaleMap.getMapY()) / (inputsInputScaleMap.getMapX() * inputsInputScaleMap.getMapY()))));

            for (double testExpansionWidth = expansionWidth; testExpansionWidth >= 0; testExpansionWidth -= expansionWidthIncrement) {

                if (randomExpansionWidthFromZero) {
                    testExpansionWidth = Math.random() * expansionWidth;
                }

                UpdatableAndExpandableFieldMapTest updatableMap = new UpdatableAndExpandableFieldMapTest(mapX, mapY, inputsInputScaleMap.getCopy(), inputsInputScaleMap.getCopy(), testExpansionWidth);

                if (testExpansionWidth - expansionWidthIncrement > 0)
                    if (updatableMap.isPerfectOverlay())
                        continue;

                System.out.println("testExpansionWidth: " + testExpansionWidth);

                System.out.println("Expansion Map Overlay: ");
                System.out.println(updatableMap.printOverlayedMaps(true, true, false, true));

                System.out.println("Comparing Target Expanded Map: ");
                System.out.println(updatableMap.printExpansionMapTest());

                System.out.println("Target Expanded Map: ");
                System.out.println(updatableMap.getTargetExpandedMap(inputsInputScaleMap));

                System.out.println("Updatable Map: ");
                System.out.println(updatableMap.getCurrentMap());
                System.out.println();

                break;
            }
        }
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

            if(randomFillCircle && randomQuadrants)
                field.drawCircleQuadrant(centerX, centerY, Math.random() > 0.5, Math.random() > 0.5, Math.random() > 0.5, Math.random() > 0.5, radius, Math.random() > 0.5);
            else if(randomFillCircle)
                field.drawCircle(centerX, centerY, radius, Math.random() > 0.5);
            else if(randomQuadrants)
                field.drawCircleQuadrant(centerX, centerY, Math.random() > 0.5, Math.random() > 0.5, Math.random() > 0.5, Math.random() > 0.5, radius, fillCircleQuadrants);
            else
                field.drawCircle(centerX, centerY, radius, fillCircleQuadrants);
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
        System.out.println(field.getCircleMap(false, true));
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
        System.out.println("Circle quadrant inputs: "+field.getInputCircleQuadrants());
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