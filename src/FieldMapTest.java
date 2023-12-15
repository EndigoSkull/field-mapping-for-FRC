import java.util.*;

public class FieldMapTest {
    private boolean[][] fieldPixelMap;

    FieldMapTest(int mapLengthX, int mapWidthY){
        fieldPixelMap = new boolean[mapWidthY][mapLengthX];
    }

    public boolean checkPixel(double x, double y){
        return checkPixel((int)x, (int)y);
    }
    public boolean checkPixel(int x, int y){
        try{
            return fieldPixelMap[y][x];
        } catch(Exception IndexOutOfBoundsException){
            return false;
        }
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

    public boolean drawCircleEmpty(double centerX, double centerY, double radius){
        return drawCircleEmpty((int)centerX, (int)centerY, radius);
    }
    public boolean drawCircleEmpty(int centerX, int centerY, double radius){
        return Bresenham.drawCircle(this, centerX, centerY, radius);
    }

    public void drawCircleFilled(double centerX, double centerY, double radius){
        drawCircleFilled((int)centerX, (int)centerY, radius);
    }
    public void drawCircleFilled(int centerX, int centerY, double radius){
        FieldMapTest tempFieldMap = new FieldMapTest(this.getMapX(), this.getMapY());

        tempFieldMap.drawCircleEmpty(centerX, centerY, radius);
        tempFieldMap.fill(centerX, centerY);

        this.addOtherMap(tempFieldMap);
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

    public int[][] getMap(){
        int[][] intMap = new int[fieldPixelMap.length][fieldPixelMap[0].length];

        for(int j = 0; j < fieldPixelMap[0].length; j++)
            for(int i = 0; i < fieldPixelMap.length; i++)
                intMap[i][j] = fieldPixelMap[i][j] ? 1 : 0;

        return intMap;
    }

    public void fill(double fillX, double fillY){
        fill((int)fillX, (int)fillY);
    }
    public void fill(int fillX, int fillY){
        ArrayList<Integer> toBeFilledX = new ArrayList<>(List.of(fillX));
        ArrayList<Integer> toBeFilledY = new ArrayList<>(List.of(fillY));
        HashSet<Integer> coordHash = new HashSet<>();
        coordHash.add(Objects.hash(fillX, fillY));

        do{
            int currentX = toBeFilledX.remove(0);
            int currentY = toBeFilledY.remove(0);
            coordHash.remove(Objects.hash(currentX, currentY));

            this.drawPixel(currentX, currentY);

            if(currentX < this.getMapX() - 1 && !this.checkPixel(currentX + 1, currentY)){
                int coordHashLength = coordHash.size();
                coordHash.add(Objects.hash(currentX + 1, currentY));
                if(coordHash.size()>coordHashLength) {
                    toBeFilledX.add(currentX + 1);
                    toBeFilledY.add(currentY);
                }
            }
            if(currentX > 0 && !this.checkPixel(currentX - 1, currentY)){
                int coordHashLength = coordHash.size();
                coordHash.add(Objects.hash(currentX - 1, currentY));
                if(coordHash.size()>coordHashLength) {
                    toBeFilledX.add(currentX - 1);
                    toBeFilledY.add(currentY);
                }
            }
            if(currentY < this.getMapY() - 1 && !this.checkPixel(currentX, currentY + 1)){
                int coordHashLength = coordHash.size();
                coordHash.add(Objects.hash(currentX, currentY + 1));
                if(coordHash.size()>coordHashLength) {
                    toBeFilledX.add(currentX);
                    toBeFilledY.add(currentY + 1);
                }
            }
            if(currentY > 0 && !this.checkPixel(currentX, currentY - 1)){
                int coordHashLength = coordHash.size();
                coordHash.add(Objects.hash(currentX, currentY - 1));
                if(coordHash.size()>coordHashLength) {
                    toBeFilledX.add(currentX);
                    toBeFilledY.add(currentY - 1);
                }
            }
        } while (!toBeFilledX.isEmpty() && !toBeFilledY.isEmpty());
    }

    public void addOtherMap(FieldMapTest otherMap){
        assert(this.mapSizeEqual(otherMap));

        for(int j = 0; j < this.getMapY(); j++)
            for(int i = 0; i < this.getMapX(); i++)
                if(!this.checkPixel(i, j) && otherMap.checkPixel(i, j))
                    this.drawPixel(i, j);
    }

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
                stringReturn.append(intMap[j][i] == 0 ? "   ": intMap[j][i]+"  ");
            stringReturn.append("\n");
        }

        return stringReturn.toString();
    }
}