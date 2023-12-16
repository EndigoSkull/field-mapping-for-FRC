import java.util.*;

public class FieldMapTest {
    private boolean[][] fieldPixelMap;

    FieldMapTest(int mapLengthX, int mapWidthY){
        fieldPixelMap = new boolean[mapWidthY][mapLengthX];
    }

    public boolean checkPixelHasObject(double x, double y){
        return checkPixelHasObject((int)x, (int)y);
    }
    public boolean checkPixelHasObject(int x, int y){
        if(x >= 0 && x < this.getMapX() && y>= 0 && y < this.getMapY())
            return fieldPixelMap[y][x];
        return false;
    }

    public boolean checkPixelOnMap(double x, double y){
        return checkPixelOnMap((int)x, (int)y);
    }
    public boolean checkPixelOnMap(int x, int y){
        return x >= 0 && x < this.getMapX() && y>= 0 && y < this.getMapY();
    }

    public boolean drawPixel(double x, double y){
        return drawPixel((int)x, (int)y);
    }
    public boolean drawPixel(int x, int y){
        try {
            boolean heldBoolean = fieldPixelMap[y][x];
            fieldPixelMap[y][x] = true;
            return true;
        } catch(Exception IndexOutOfBoundsException){
            return false;
        }
    }

    public boolean removePixel(double x, double y){
        return removePixel((int)x, (int)y);
    }
    public boolean removePixel(int x, int y){
        try {
            boolean heldBoolean = fieldPixelMap[y][x];
            fieldPixelMap[y][x] = false;
            return true;
        } catch(Exception IndexOutOfBoundsException){
            return false;
        }
    }

    public boolean drawLine(double x1, double y1, double x2, double y2){
        return drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }
    public boolean drawLine(int x1, int y1, int x2, int y2){
        return Bresenham.drawLine(this, x1, y1, x2, y2);
    }

    public void drawCircle(double centerX, double centerY, double radius, boolean fillCircle){
        this.drawCircle((int)centerX, (int)centerY, radius, fillCircle);
    }
    public void drawCircle(int centerX, int centerY, double radius, boolean fillCircle){
        FieldMapTest tempFieldMap = new FieldMapTest(this.getMapX(), this.getMapY());

        Bresenham.drawCircle(tempFieldMap, centerX, centerY, radius);
        if(fillCircle)
            tempFieldMap.fillSimplePolygon(centerX, centerY);

        this.addOtherMap(tempFieldMap);
    }

    public void drawPolygonDouble(List<Double> verticesX, List<Double> verticesY, boolean fillPolygon){
        ArrayList<Integer> intVerticesX = new ArrayList<>();
        ArrayList<Integer> intVerticesY = new ArrayList<>();

        verticesX.forEach(a -> intVerticesX.add(a.intValue()));
        verticesY.forEach(a -> intVerticesY.add(a.intValue()));

        drawPolygonInteger(intVerticesX, intVerticesY, fillPolygon);
    }
    public void drawPolygonInteger(List<Integer> verticesX, List<Integer> verticesY, boolean fillPolygon){
        int[] intVerticesX = new int[verticesX.size()];
        int[] intVerticesY = new int[verticesY.size()];

        for(int i = 0; i < intVerticesX.length; i++)
            intVerticesX[i] = verticesX.get(i);
        for(int i = 0; i < intVerticesY.length; i++)
            intVerticesY[i] = verticesY.get(i);

        drawPolygon(intVerticesX, intVerticesY, fillPolygon);
    }
    public void drawPolygon(double[] verticesX, double[] verticesY, boolean fillPolygon) {
        int[] intVerticesX = new int[verticesX.length];
        int[] intVerticesY = new int[verticesY.length];

        for (int i = 0; i < intVerticesX.length; i++)
            intVerticesX[i] = (int) verticesX[i];
        for (int i = 0; i < intVerticesY.length; i++)
            intVerticesY[i] = (int) verticesY[i];

        drawPolygon(intVerticesX, intVerticesY, fillPolygon);
    }
    public void drawPolygon(int[] verticesX, int[] verticesY, boolean fillPolygon){
        assert(verticesX.length == verticesY.length);

        if(fillPolygon) {
            FieldMapTest tempFieldMap = new FieldMapTest(this.getMapX(), this.getMapY());

            for(int i = 0; i < verticesX.length - 1; i++)
                tempFieldMap.drawLine(verticesX[i], verticesY[i], verticesX[i+1], verticesY[i+1]);
            tempFieldMap.drawLine(verticesX[verticesX.length-1], verticesY[verticesY.length-1], verticesX[0], verticesY[0]);

            tempFieldMap.fillComplexPolygon(verticesX, verticesY);

            this.addOtherMap(tempFieldMap);

//            System.out.println("Temp Field Map:");
//            System.out.println(tempFieldMap);
        }
        else {
            for (int i = 0; i < verticesX.length - 1; i++)
                this.drawLine(verticesX[i], verticesY[i], verticesX[i + 1], verticesY[i + 1]);
            this.drawLine(verticesX[verticesX.length - 1], verticesY[verticesY.length - 1], verticesX[0], verticesY[0]);
        }
    }

    public int getMapY(){
        return fieldPixelMap.length;
    }
    public int getMapX(){
        return fieldPixelMap[0].length;
    }

    public boolean mapSizeEqual(FieldMapTest otherMap){
        return this.getMapX() == otherMap.getMapX() && this.getMapY() == otherMap.getMapY();
    }

    public boolean[][] getBaseBoolMap() {
        return fieldPixelMap;
    }
    public int[][] getBaseIntMap(){
        int[][] intMap = new int[fieldPixelMap.length][fieldPixelMap[0].length];

        for(int j = 0; j < fieldPixelMap[0].length; j++)
            for(int i = 0; i < fieldPixelMap.length; i++)
                intMap[i][j] = fieldPixelMap[i][j] ? 1 : 0;

        return intMap;
    }

    public FieldMapTest getCopy(){
        FieldMapTest mapCopy = new FieldMapTest(this.getMapX(), this.getMapY());
        mapCopy.addOtherMap(this);
        return mapCopy;
    }

    public void fillSimplePolygon(double fillX, double fillY){
        fillSimplePolygon((int)fillX, (int)fillY);
    }
    public void fillSimplePolygon(int fillX, int fillY){
        fillSimplePolygon(fillX, fillY, this.getMapX()-1, 0, this.getMapY()-1, 0);
    }
    public void fillSimplePolygon(int fillX, int fillY, int xMax, int xMin, int yMax, int yMin){
        assert(fillX<xMax && fillX>xMin && fillY<yMax && fillY>yMin);

        ArrayList<Integer> toBeFilledX = new ArrayList<>(List.of(fillX));
        ArrayList<Integer> toBeFilledY = new ArrayList<>(List.of(fillY));
        HashSet<Integer> coordHash = new HashSet<>();
        coordHash.add(Objects.hash(fillX, fillY));

        do{
            int currentX = toBeFilledX.remove(0);
            int currentY = toBeFilledY.remove(0);
            coordHash.remove(Objects.hash(currentX, currentY));

            this.drawPixel(currentX, currentY);

            if(currentX < xMax && this.checkPixelOnMap(currentX + 1, currentY) && !this.checkPixelHasObject(currentX + 1, currentY)){
                int coordHashLength = coordHash.size();
                coordHash.add(Objects.hash(currentX + 1, currentY));
                if(coordHash.size()>coordHashLength) {
                    toBeFilledX.add(currentX + 1);
                    toBeFilledY.add(currentY);
                }
            }
            if(currentX > xMin && this.checkPixelOnMap(currentX - 1, currentY) && !this.checkPixelHasObject(currentX - 1, currentY)){
                int coordHashLength = coordHash.size();
                coordHash.add(Objects.hash(currentX - 1, currentY));
                if(coordHash.size()>coordHashLength) {
                    toBeFilledX.add(currentX - 1);
                    toBeFilledY.add(currentY);
                }
            }
            if(currentY < yMax && this.checkPixelOnMap(currentX, currentY + 1) && !this.checkPixelHasObject(currentX, currentY + 1)){
                int coordHashLength = coordHash.size();
                coordHash.add(Objects.hash(currentX, currentY + 1));
                if(coordHash.size()>coordHashLength) {
                    toBeFilledX.add(currentX);
                    toBeFilledY.add(currentY + 1);
                }
            }
            if(currentY > yMin && this.checkPixelOnMap(currentX, currentY - 1) && !this.checkPixelHasObject(currentX, currentY - 1)){
                int coordHashLength = coordHash.size();
                coordHash.add(Objects.hash(currentX, currentY - 1));
                if(coordHash.size()>coordHashLength) {
                    toBeFilledX.add(currentX);
                    toBeFilledY.add(currentY - 1);
                }
            }
        } while (!toBeFilledX.isEmpty() && !toBeFilledY.isEmpty());
    }

    //TODO Doesn't work with especially concave polygons
    public void fillComplexPolygon(int[] verticesX, int[] verticesY){
        int xMax = 0;
        int xMin = this.getMapX()-1;
        int yMax = 0;
        int yMin = this.getMapY()-1;

        for(int i = 0; i < verticesX.length; i++) {
            xMax = Math.max(verticesX[i], xMax);
            xMin = Math.min(verticesX[i], xMin);
        }
        for(int i = 0; i < verticesY.length; i++) {
            yMax = Math.max(verticesY[i], yMax);
            yMin = Math.min(verticesY[i], yMin);
        }

        xMax++;
        xMin--;
        yMax++;
        yMin--;

        FieldMapTest otherMap = this.getCopy();

        if(xMin >= 0 && yMin >= 0)
            otherMap.fillSimplePolygon(xMin, yMin, xMax, xMin, yMax, yMin);
        else if(xMax < this.getMapX() && yMax < this.getMapY())
            otherMap.fillSimplePolygon(xMax, yMax, xMax, xMin, yMax, yMin);
        else if(xMin >= 0 && yMax < this.getMapY())
            otherMap.fillSimplePolygon(xMin, yMax, xMax, xMin, yMax, yMin);
        else if(xMax < this.getMapX() && yMin >= 0)
            otherMap.fillSimplePolygon(xMax, yMin, xMax, xMin, yMax, yMin);
        else
            System.out.println("Couldn't find a corner");

//        System.out.println("Other Map:");
//        System.out.println(otherMap);

        this.inverseAddOtherMap(otherMap, xMax, xMin, yMax, yMin);
    }

    public void addOtherMap(FieldMapTest otherMap){
        assert(this.mapSizeEqual(otherMap));

        this.addOtherMap(otherMap, this.getMapX(), 0, this.getMapY(), 0);
    }
    public void addOtherMap(FieldMapTest otherMap, int xMax, int xMin, int yMax, int yMin){
        assert(this.mapSizeEqual(otherMap));

        for(int j = yMin; j < yMax; j++)
            for(int i = xMin; i < xMax; i++)
                if(!this.checkPixelHasObject(i, j) && otherMap.checkPixelHasObject(i, j))
                    this.drawPixel(i, j);
    }

    public void inverseAddOtherMap(FieldMapTest otherMap){
        assert(this.mapSizeEqual(otherMap));

        this.inverseAddOtherMap(otherMap, this.getMapX(), 0, this.getMapY(), 0);
    }
    public void inverseAddOtherMap(FieldMapTest otherMap, int xMax, int xMin, int yMax, int yMin){
        assert(this.mapSizeEqual(otherMap));

        for(int j = yMin; j < yMax; j++)
            for(int i = xMin; i < xMax; i++)
                if(!this.checkPixelHasObject(i, j) && !otherMap.checkPixelHasObject(i, j))
                    this.drawPixel(i, j);
    }

    public String toString(){
        int[][] intMap = getBaseIntMap();

        StringBuilder stringReturn = new StringBuilder();

        stringReturn.append("   ");
        for(int i = 0; i < intMap[0].length; i++)
            stringReturn.append(i > 9 ? i+" " : i+"  ");
        stringReturn.append("\n");

        for(int j = 0; j < intMap.length; j++) {
            stringReturn.append(j > 9 ? j+" " : j+"  ");
            for (int i = 0; i < intMap[0].length; i++)
                stringReturn.append(intMap[j][i] == 0 ? "   ": intMap[j][i]+"  ");
            stringReturn.append("\n");
        }

        return stringReturn.toString();
    }
}