import java.util.ArrayList;

public class FieldMapWithInputTest extends FieldMapTest{
    private ArrayList<Integer> inputLineX = new ArrayList<>();
    private ArrayList<Integer> inputLineY = new ArrayList<>();
    private ArrayList<Integer> inputLineCount = new ArrayList<>();
    private ArrayList<Integer> inputCircleX = new ArrayList<>();
    private ArrayList<Integer> inputCircleY = new ArrayList<>();
    private ArrayList<Double> inputCircleRadius = new ArrayList<Double>();
    private ArrayList<Integer> inputCircleCount = new ArrayList<>();

    FieldMapWithInputTest(int mapLengthX, int mapWidthY){
        super(mapLengthX, mapWidthY);
    }

    public boolean addLineInput(int x, int y){
        for(int i = 0; i < inputLineX.size(); i++)
            if(inputLineX.get(i) == x && inputLineY.get(i) == y) {
                inputLineCount.add(i, inputLineCount.get(i) + 1);
                return false;
            }
        inputLineX.add(x);
        inputLineY.add(y);
        inputLineCount.add(1);
        return true;
    }

    public boolean addCircleInput(int x, int y, double radius){
        for(int i = 0; i < inputCircleX.size(); i++)
            if(inputCircleX.get(i) == x && inputCircleY.get(i) == y && inputCircleRadius.get(i) < 1E-6) {
                inputCircleCount.add(i, inputCircleCount.get(i) + 1);
                return false;
            }
        inputCircleX.add(x);
        inputCircleY.add(y);
        inputCircleRadius.add(radius);
        inputCircleCount.add(1);
        return true;
    }

    @Override
    public boolean drawLine(double x1, double y1, double x2, double y2){
        return drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }
    @Override
    public boolean drawLine(int x1, int y1, int x2, int y2){
        this.addLineInput(x1, y1);
        this.addLineInput(x2, y2);

        return super.drawLine(x1, y1, x2, y2);
    }

    @Override
    public boolean drawCircleEmpty(double centerX, double centerY, double radius){
        return this.drawCircleEmpty((int)centerX, (int)centerY, radius);
    }
    @Override
    public boolean drawCircleEmpty(int centerX, int centerY, double radius){
        addCircleInput(centerX, centerY, radius);

        return super.drawCircleEmpty(centerX, centerY, radius);
    }

    @Override
    public void drawCircleFilled(double centerX, double centerY, double radius) {
        this.drawCircleFilled((int)centerX, (int)centerY, radius);
    }
    @Override
    public void drawCircleFilled(int centerX, int centerY, double radius) {
        addCircleInput(centerX, centerY, radius);

        super.drawCircleFilled(centerX, centerY, radius);
    }

    @Override
    public int[][] getMap(){
        int[][] intMap = super.getMap();

        for(int i = 0; i < inputLineX.size(); i++)
            intMap[inputLineY.get(i)][inputLineX.get(i)] += inputLineCount.get(i)*2;

        for(int i = 0; i < inputCircleX.size(); i++)
            intMap[inputCircleY.get(i)][inputCircleX.get(i)] += inputCircleCount.get(i)*20;

        return intMap;
    }

    public String toStringSuper(){
        return super.toString();
    }

    @Override
    public String toString(){
        int[][] intMap = getMap();

        StringBuilder stringReturn = new StringBuilder();

        stringReturn.append("   ");
        for(int i = 0; i < intMap[0].length; i++)
            stringReturn.append(i > 9 ? i+" " : i+"  ");
        stringReturn.append("\n");

        for(int j = 0; j < intMap.length; j++) {
            stringReturn.append(j > 9 ? j+" " : j+"  ");
            for (int i = 0; i < intMap[0].length; i++)
                stringReturn.append(intMap[j][i] == 0 ? "   ": intMap[j][i]>9 ? intMap[j][i]+" " : intMap[j][i]+"  ");
            stringReturn.append("\n");
        }

        return stringReturn.toString();
    }


//    public int[][] printOverlayedPointsDouble(List<Double> inputX, List<Double> inputY){
//        ArrayList<Integer> outputX = new ArrayList<>();
//        ArrayList<Integer> outputY = new ArrayList<>();
//
//        inputX.forEach(x -> outputX.add((int)(double)x));
//        inputY.forEach(y -> outputY.add((int)(double)y));
//
//        return printOverlayedPointsInt(outputX, outputY);
//    }
//    public int[][] printOverlayedPointsInt(List<Integer> inputX, List<Integer> inputY){
//        StringBuilder stringReturn = new StringBuilder();
//
//        assert(inputX.size() == inputY.size());
//
//        int currentPixelInt = 0;
//
//        for(int j = 0; j < this.getMapY(); j++)
//            for(int i = 0; i < this.getMapX(); i++) {
//                currentPixelInt = this.checkPixel(i, j) ? 1 : 0;
//                for (int k = 0; k < inputX.size(); k++)
//                    if (inputX.get(k) == i && inputY.get(k) == j)
//                        if(currentPixelInt < 2)
//                            currentPixelInt = 2;
//                        else
//                            currentPixelInt++;
//                stringReturn.append(currentPixelInt+" ");
//            }
//        return stringReturn.toString();
//    }
}
