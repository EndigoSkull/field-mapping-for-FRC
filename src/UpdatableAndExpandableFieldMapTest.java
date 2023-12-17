public class UpdatableAndExpandableFieldMapTest {
    private final FieldMapTest stableMap;
    private final FieldMapTest updatableMap;
    private FieldMapTest currentMap;

    private boolean updatableMapChanged;

    private double expansionWidth;

    /**
     * @param mapLengthX
     * @param mapWidthY
     * @param stableMap a FieldMapTest of all nonupdating obstacles of the field
     * @param updatableMap a FieldMapTest of all updating obstacles of the field
     */
    public UpdatableAndExpandableFieldMapTest(int mapLengthX, int mapWidthY, FieldMapTest stableMap, FieldMapTest updatableMap, double expansionWidth){
        assert(stableMap.mapSizeEqual(updatableMap));
        assert(stableMap.getMapX() == mapLengthX && stableMap.getMapY() == mapWidthY);

        this.stableMap = stableMap.getCopy();
        stableMap = getExpandedMap(stableMap, expansionWidth);

        this.updatableMap = updatableMap.getCopy();

        currentMap = new FieldMapTest(mapLengthX, mapWidthY);
        currentMap.addOtherMap(stableMap);
        currentMap.addOtherMap(getExpandedMap(updatableMap, expansionWidth));

        updatableMapChanged = false;

        this.expansionWidth = expansionWidth;
    }

    public FieldMapTest getExpandedMap(FieldMapTest map, double expansionWidth){
        FieldMapTest expandedMap = map.getCopy();

        for(int j = 0; j < map.getMapY(); j++){
            for(int i = 0; i < map.getMapX(); i++){
                if(map.checkPixelHasObject(i, j)){
                    boolean north = map.checkPixelHasObjectOrOffMap(i, j+1);
                    boolean east = map.checkPixelHasObjectOrOffMap(i+1, j);
                    boolean south = map.checkPixelHasObjectOrOffMap(i, j-1);
                    boolean west = map.checkPixelHasObjectOrOffMap(i-1, j);

                    int xShift = 0;
                    int yShift = 0;
                    int numNeighbors = 0;

                    if(north){
                        numNeighbors++;
                        xShift--;
                    }
                    if(east){
                        numNeighbors++;
                        yShift--;
                    }
                    if(south){
                        numNeighbors++;
                        xShift++;
                    }
                    if(west){
                        numNeighbors++;
                        yShift++;
                    }

                    if(numNeighbors == 4)
                        break;
                    else if(numNeighbors == 3)
                        for(int stepCoefficient = 1; stepCoefficient < expansionWidth+1; stepCoefficient++)
                            expandedMap.drawPixel(i+(xShift*stepCoefficient), j+(yShift*stepCoefficient));
                    else if(numNeighbors == 0 || numNeighbors == 1)
                        expandedMap.drawCircle(i, j, expansionWidth, true);
                    else if(north && south)
                        for(int stepCoefficient = 1; stepCoefficient < expansionWidth+1; stepCoefficient++) {
                            expandedMap.drawPixel(i + stepCoefficient, j);
                            expandedMap.drawPixel(i - stepCoefficient, j);
                        }
                    else if(east && west)
                        for(int stepCoefficient = 1; stepCoefficient < expansionWidth+1; stepCoefficient++) {
                            expandedMap.drawPixel(i, j + stepCoefficient);
                            expandedMap.drawPixel(i, j - stepCoefficient);
                        }
                    else{
                        boolean quadrant1 = !(north || east);
                        boolean quadrant2 = !(west || north);
                        boolean quadrant3 = !(south || west);
                        boolean quadrant4 = !(east || south);

                        expandedMap.drawCircleQuadrant(i, j, quadrant1, quadrant2, quadrant3, quadrant4, expansionWidth, true);
                    }
                }
            }
        }

        return expandedMap;
    }

    public void updateCurrentMap(){
        if(updatableMapChanged) {
            currentMap = stableMap.getCopy();
            currentMap.addOtherMap(getExpandedMap(updatableMap, expansionWidth));
        }
    }

    public FieldMapTest updatableMap(){
        updatableMapChanged = true;

        return updatableMap;
    }
}