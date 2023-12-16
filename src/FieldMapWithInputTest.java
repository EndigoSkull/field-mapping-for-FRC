import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FieldMapWithInputTest extends FieldMapTest{
    private final ArrayList<Integer> inputLineX = new ArrayList<>();
    private final ArrayList<Integer> inputLineY = new ArrayList<>();
    private final ArrayList<Integer> inputLineCount = new ArrayList<>();

    private final ArrayList<Integer> inputCircleX = new ArrayList<>();
    private final ArrayList<Integer> inputCircleY = new ArrayList<>();
    private final ArrayList<Double> inputCircleRadius = new ArrayList<>();
    private final ArrayList<Boolean> inputCircleFilled = new ArrayList<>();
    private final ArrayList<Integer> inputCircleCount = new ArrayList<>();

    private final ArrayList<int[]> inputPolygonX = new ArrayList<>();
    private final ArrayList<int[]> inputPolygonY = new ArrayList<int[]>();
    private final ArrayList<Boolean> inputPolygonFilled = new ArrayList<>();
    private final ArrayList<Integer> inputPolygonCount = new ArrayList<>();

    FieldMapWithInputTest(int mapLengthX, int mapWidthY){
        super(mapLengthX, mapWidthY);
    }

    public boolean addLineInput(int x, int y){
        for(int i = 0; i < inputLineX.size(); i++)
            if(inputLineX.get(i) == x && inputLineY.get(i) == y) {
                inputLineCount.set(i, inputLineCount.get(i) + 1);
                return false;
            }
        inputLineX.add(x);
        inputLineY.add(y);
        inputLineCount.add(1);
        return true;
    }

    public boolean addCircleInput(int x, int y, double radius, boolean circleFilled){
        for(int i = 0; i < inputCircleX.size(); i++)
            if(inputCircleX.get(i) == x && inputCircleY.get(i) == y && inputCircleRadius.get(i) < 1E-6) {
                inputCircleCount.set(i, inputCircleCount.get(i) + 1);
                return false;
            }
        inputCircleX.add(x);
        inputCircleY.add(y);
        inputCircleRadius.add(radius);
        inputCircleFilled.add(circleFilled);
        inputCircleCount.add(1);
        return true;
    }

    public boolean addPolygonInput(int[] verticesX, int[] verticesY, boolean polygonFilled){
        for(int i = 0; i < inputPolygonX.size(); i ++) {
            boolean same = true;
            for (int j = 0; j < inputPolygonX.get(i).length; j++)
                if (!Objects.equals(inputPolygonX.get(i)[j], inputPolygonY.get(i)[j]))
                    same = false;
            if(same){
                inputPolygonCount.set(i, inputPolygonCount.get(i)+1);
                return false;
            }
        }
        inputPolygonX.add(verticesX);
        inputPolygonY.add(verticesY);
        inputPolygonFilled.add(polygonFilled);
        inputPolygonCount.add(1);
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
    public void drawCircle(double centerX, double centerY, double radius, boolean fillCircle) {
        this.drawCircle((int)centerX, (int)centerY, radius, fillCircle);
    }
    @Override
    public void drawCircle(int centerX, int centerY, double radius, boolean fillCircle) {
        addCircleInput(centerX, centerY, radius, fillCircle);

        super.drawCircle(centerX, centerY, radius, fillCircle);
    }

    @Override
    public void drawPolygonDouble(List<Double> verticesX, List<Double> verticesY, boolean fillPolygon){
        ArrayList<Integer> intVerticesX = new ArrayList<>();
        ArrayList<Integer> intVerticesY = new ArrayList<>();

        verticesX.forEach(a -> intVerticesX.add(a.intValue()));
        verticesY.forEach(a -> intVerticesY.add(a.intValue()));

        drawPolygonInteger(intVerticesX, intVerticesY, fillPolygon);
    }
    @Override
    public void drawPolygonInteger(List<Integer> verticesX, List<Integer> verticesY, boolean fillPolygon){
        int[] intVerticesX = new int[verticesX.size()];
        int[] intVerticesY = new int[verticesY.size()];

        for(int i = 0; i < intVerticesX.length; i++)
            intVerticesX[i] = verticesX.get(i);
        for(int i = 0; i < intVerticesY.length; i++)
            intVerticesY[i] = verticesY.get(i);

        drawPolygon(intVerticesX, intVerticesY, fillPolygon);
    }
    @Override
    public void drawPolygon(double[] verticesX, double[] verticesY, boolean fillPolygon) {
        int[] intVerticesX = new int[verticesX.length];
        int[] intVerticesY = new int[verticesY.length];

        for (int i = 0; i < intVerticesX.length; i++)
            intVerticesX[i] = (int) verticesX[i];
        for (int i = 0; i < intVerticesY.length; i++)
            intVerticesY[i] = (int) verticesY[i];

        drawPolygon(intVerticesX, intVerticesY, fillPolygon);
    }
    @Override
    public void drawPolygon(int[] verticesX, int[] verticesY, boolean fillPolygon){
        this.addPolygonInput(verticesX, verticesY, fillPolygon);

        super.drawPolygon(verticesX, verticesY, fillPolygon);
    }

    public int[][] getCombinedMap(){
        int[][] intMap = super.getBaseIntMap();

        for(int i = 0; i < inputLineX.size(); i++)
            intMap[inputLineY.get(i)][inputLineX.get(i)] += inputLineCount.get(i)*2;

        for(int i = 0; i < inputCircleX.size(); i++)
            intMap[inputCircleY.get(i)][inputCircleX.get(i)] += inputCircleCount.get(i)*20;

        return intMap;
    }
    public FieldMapTest getBaseMap(){
        return super.getCopy();
    }
    public FieldMapTest getLineInputMap(){
        FieldMapTest lineInputMap = new FieldMapTest(this.getMapX(), this.getMapY());

        for(int i = 0; i < inputLineX.size(); i++){
            lineInputMap.drawPixel(inputLineX.get(i), inputLineY.get(i));
        }

        return lineInputMap;
    }
    public FieldMapTest getLineMap(){
        FieldMapTest lineMap = new FieldMapTest(this.getMapX(), this.getMapY());

        for(int i = 0; i < inputLineX.size()-1; i++){
            lineMap.drawLine(inputLineX.get(i), inputLineY.get(i), inputLineX.get(i+1), inputLineY.get(i+1));
        }
        lineMap.drawLine(inputLineX.get(inputLineX.size()-1), inputLineY.get(inputLineX.size()-1), inputLineX.get(0), inputLineY.get(0));

        return lineMap;
    }
    public FieldMapTest getCircleInputMap(){
        FieldMapTest circleInputMap = new FieldMapTest(this.getMapX(), this.getMapY());

        for(int i = 0; i < inputCircleX.size(); i++){
            circleInputMap.drawPixel(inputCircleX.get(i), inputCircleY.get(i));
        }

        return circleInputMap;
    }
    public FieldMapTest getCircleMap(boolean fillFromInputs, boolean fillCircle){
        FieldMapTest circleMap = new FieldMapTest(this.getMapX(), this.getMapY());

        for(int i = 0; i < inputCircleX.size(); i++){
            if(fillFromInputs && fillCircle)
                circleMap.drawCircle(inputCircleX.get(i), inputCircleY.get(i), inputCircleRadius.get(i), true);
            else if(fillFromInputs && !fillCircle)
                circleMap.drawCircle(inputCircleX.get(i), inputCircleY.get(i), inputCircleRadius.get(i), false);
            else
                circleMap.drawCircle(inputCircleX.get(i), inputCircleY.get(i), inputCircleRadius.get(i), inputCircleFilled.get(i));
        }

        return circleMap;
    }

    public ArrayList<Integer> getInputLineX(){
        return (new ArrayList<>(List.copyOf(inputLineX)));
    }
    public ArrayList<Integer> getInputLineY(){
        return (new ArrayList<>(List.copyOf(inputLineY)));
    }
    public ArrayList<Integer> getInputLineCount(){
        return (new ArrayList<>(List.copyOf(inputLineCount)));
    }
    public ArrayList<Integer> getInputCircleX(){
        return (new ArrayList<>(List.copyOf(inputCircleX)));
    }
    public ArrayList<Integer> getInputCircleY(){
        return (new ArrayList<>(List.copyOf(inputCircleY)));
    }
    public ArrayList<Double> getInputCircleRadius(){
        return (new ArrayList<>(List.copyOf(inputCircleRadius)));
    }
    public ArrayList<Boolean> getInputCircleFilled(){
        return (new ArrayList<>(List.copyOf(inputCircleFilled)));
    }
    public ArrayList<Integer> getInputCircleCount(){
        return (new ArrayList<>(List.copyOf(inputCircleCount)));
    };
    public ArrayList<int[]> getInputPolygonX(){
        return (new ArrayList<>(List.copyOf(inputPolygonX)));
    }
    public ArrayList<int[]> getInputPolygonY(){
        return (new ArrayList<>(List.copyOf(inputPolygonY)));
    }
    private ArrayList<Boolean> getInputPolygonFilled(){
        return (new ArrayList<>(List.copyOf(inputPolygonFilled)));
    }
    private ArrayList<Integer> getInputPolygonCount(){
        return (new ArrayList<>(List.copyOf(inputPolygonCount)));
    }

    public String toStringSuper(){
        return super.toString();
    }

    @Override
    public String toString(){
        int[][] intMap = this.getCombinedMap();

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
