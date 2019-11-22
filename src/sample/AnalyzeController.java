package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

//import sample.Rune;

public class AnalyzeController implements Initializable {


    @FXML private TableView<Rune> runeTable;
    @FXML private TableColumn<Rune, String> colSet;
    @FXML private TableColumn<Rune, Integer> colSlot;
    @FXML private TableColumn<Rune, String> colStars;
    @FXML private TableColumn<Rune, String> colRarity;
    @FXML private TableColumn<Rune, Integer> colLevel;
    @FXML private TableColumn<Rune, String> colAncient;
    @FXML private TableColumn<Rune, Double> colEffCur;
    @FXML private TableColumn<Rune, Double> colEffMax;
    @FXML private TableColumn<Rune, String> colMain;
    @FXML private TableColumn<Rune, Integer> colMainVal;
    @FXML private TableColumn<Rune, String> colInn;
    @FXML private TableColumn<Rune, Integer> colInnVal;
    @FXML private TableColumn<Rune, String> colSub1;
    @FXML private TableColumn<Rune, Integer> colSub1Val;
    @FXML private TableColumn<Rune, String> colSub2;
    @FXML private TableColumn<Rune, Integer> colSub2Val;
    @FXML private TableColumn<Rune, String> colSub3;
    @FXML private TableColumn<Rune, Integer> colSub3Val;
    @FXML private TableColumn<Rune, String> colSub4;
    @FXML private TableColumn<Rune, Integer> colSub4Val;

    public File runeFile;
    private List<Rune> tableRuneList = new ArrayList<>();
    private Map<Integer, String> mainStatID;
    private Map<Integer, String> rarityID;
    private Map<Integer, String> runeSetID;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        colSet.setCellValueFactory(new PropertyValueFactory<>("set"));
        colSlot.setCellValueFactory(new PropertyValueFactory<>("slot"));
        colStars.setCellValueFactory(new PropertyValueFactory<>("stars"));
        colRarity.setCellValueFactory(new PropertyValueFactory<>("rarity"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        colAncient.setCellValueFactory(new PropertyValueFactory<>("ancient"));
        colEffCur.setCellValueFactory(new PropertyValueFactory<>("effCur"));
        colEffMax.setCellValueFactory(new PropertyValueFactory<>("effMax"));
        colMain.setCellValueFactory(new PropertyValueFactory<>("mainStat"));
        colMainVal.setCellValueFactory(new PropertyValueFactory<>("mainStatVal"));
        colInn.setCellValueFactory(new PropertyValueFactory<>("innateStat"));
        colInnVal.setCellValueFactory(new PropertyValueFactory<>("innateStatVal"));
        colSub1.setCellValueFactory(new PropertyValueFactory<>("sub1Stat"));
        colSub1Val.setCellValueFactory(new PropertyValueFactory<>("sub1StatVal"));
        colSub2.setCellValueFactory(new PropertyValueFactory<>("sub2Stat"));
        colSub2Val.setCellValueFactory(new PropertyValueFactory<>("sub2StatVal"));
        colSub3.setCellValueFactory(new PropertyValueFactory<>("sub3Stat"));
        colSub3Val.setCellValueFactory(new PropertyValueFactory<>("sub3StatVal"));
        colSub4.setCellValueFactory(new PropertyValueFactory<>("sub4Stat"));
        colSub4Val.setCellValueFactory(new PropertyValueFactory<>("sub4StatVal"));
    }

    public void jsonTest() throws Exception {
        List<Integer> listNumbers = Arrays.asList(1,3,5);
        Set<Integer> flats = new HashSet<>(listNumbers);

        mainStatID = createMainStatMap();
        rarityID = createRarityMap();
        runeSetID = createSetIDMap();

        String jsonContents = new String(Files.readAllBytes(Paths.get(runeFile.getAbsolutePath())));
        JSONObject jo = new JSONObject(jsonContents);
        JSONArray runeArray = jo.getJSONArray("runes");


        ObservableList names = FXCollections.observableArrayList();

        for (int i = 0; i < runeArray.length(); i++) {
            JSONObject rune = runeArray.getJSONObject(i);

            //if (rune.getJSONArray("pri_eff").getInt(0) == 5) {
            //    addSellRuneToTableList(rune);
            //}



            // Check for flat main stat on 2,4,6 runes.
            if (rune.getInt("slot_no") == 2 || rune.getInt("slot_no") == 4 || rune.getInt("slot_no") == 6) {
                JSONArray mainStat = rune.getJSONArray("pri_eff");
                //System.out.println(mainStat.getInt(0));
                if (flats.contains(mainStat.getInt(0))) {
                    //System.out.println("Sell!!!");
                    //names.add(outputGenerator(rune));
                    addSellRuneToTableList(rune);
                }
            } else if (rune.getInt("upgrade_curr") == 0) {
                if (rune.getInt("class") < 5) { // Check if rune is 1 - 4*
                    //names.add(outputGenerator(rune));
                    addSellRuneToTableList(rune);
                } else if (rune.getInt("rank") < 4) { // Check if rune is rare or lower
                    //names.add(outputGenerator(rune));
                    addSellRuneToTableList(rune);
                } else if (rune.getInt("rank") == 4) {
                    JSONArray subStats = rune.getJSONArray("sec_eff");
                    for (int j = 0; j < subStats.length(); j++) { // Check if any of the hero rune substats are flat.
                        JSONArray tempsub = (JSONArray) subStats.get(j);
                        if (flats.contains(tempsub.get(0))) {
                            //names.add(outputGenerator(rune));
                            addSellRuneToTableList(rune);
                            break;
                        }
                    }
                } else if (rune.getInt("rank") == 5) {
                    JSONArray subStats = rune.getJSONArray("sec_eff");
                    int flatAmount = 0;
                    for (int j = 0; j < subStats.length(); j++) { // Check if more than 1 of the legend rune substats is flat.
                        JSONArray tempsub = (JSONArray) subStats.get(j);
                        if (flats.contains(tempsub.get(0))) {
                            flatAmount++;
                            if (flatAmount > 1) {
                                //names.add(outputGenerator(rune));
                                addSellRuneToTableList(rune);
                                break;
                            }
                        }
                    }
                }
            }
        }

        //runeList.setItems(names);
        ObservableList data = FXCollections.observableList(tableRuneList);
        runeTable.setItems(data);
        runeTable.getSortOrder().add(colSet);
        runeTable.getSortOrder().add(colSlot);
        runeTable.sort();
        autoResizeColumns(runeTable);
        autoResizeScene(runeTable);

    }

    private String outputGenerator (JSONObject rune) {
        StringBuilder runeInfo = new StringBuilder();
        runeInfo.append(runeSetID.get(rune.getInt("set_id")));              //Rune set name
        runeInfo.append(" Slot ");
        runeInfo.append(rune.getInt("slot_no")+" ");                        //Rune slot number
        runeInfo.append(rune.getInt("class")%10+"* ");                      //Number of stars, %10 accounts for ancient rune
        runeInfo.append(rarityID.get(rune.getInt("rank")));                 //Current rarity
        runeInfo.append(" Lvl "+rune.getInt("upgrade_curr"));               //Current upgrade lvl
        JSONArray mainStat = rune.getJSONArray("pri_eff");
        runeInfo.append(" Main: "+mainStatID.get(mainStat.getInt(0)));     //Main stat
        runeInfo.append(" "+mainStat.getInt(1));                           //Main stat value
        JSONArray innate = rune.getJSONArray("prefix_eff");
        if (innate.getInt(0) != 0) {
            runeInfo.append(" Prefix: "+mainStatID.get(innate.getInt(0)));   //Prefix stat
            runeInfo.append(" "+innate.getInt(1));                           //Prefix stat value
        }
        JSONArray subStats = rune.getJSONArray("sec_eff");
        for (int i = 0; i < subStats.length(); i++) {
            JSONArray tempsub = (JSONArray) subStats.get(i);                        //Sub stat list
            runeInfo.append(" Sub"+(i+1)+": "+mainStatID.get(tempsub.get(0)));          //Sub stat
            runeInfo.append(" "+((Integer)tempsub.get(1)+(Integer)tempsub.get(3))); //Sub stat value, regular + grind
        }

        return runeInfo.toString();
    }

    private void addSellRuneToTableList (JSONObject rune) throws IOException {
        String set = runeSetID.get(rune.getInt("set_id"));
        Integer slot = rune.getInt("slot_no");
        String stars = rune.getInt("class")%10+"*";
        String rarity = rarityID.get(rune.getInt("rank")%10);
        Integer level = rune.getInt("upgrade_curr");
        String ancient = "No";
        if (rune.getInt("class") > 6) {
            ancient = "Yes";
        }

        ArrayList<Double> effs = calculateEfficiency(rune);
        double effCur = effs.get(0);
        double effMax = effs.get(1);
        JSONArray mS = rune.getJSONArray("pri_eff");
        String mainStat = mainStatID.get(mS.getInt(0));
        Integer mainStatVal = mS.getInt(1);

        JSONArray inn = rune.getJSONArray("prefix_eff");
        String innStat = "";
        Integer innStatVal = -1;
        if (inn.getInt(0) != 0) {
            innStat = mainStatID.get(inn.getInt(0));
            innStatVal = inn.getInt(1);
        }

        JSONArray subs = rune.getJSONArray("sec_eff");
        JSONArray tempsub = (JSONArray) subs.get(0);
        String subStat1 = mainStatID.get(tempsub.get(0));
        Integer subStat1Val = ((Integer)tempsub.get(1)+(Integer)tempsub.get(3));


        String subStat2 = "";
        Integer subStat2Val = -1;
        if (subs.length() > 1) {
            tempsub = (JSONArray) subs.get(1);
            subStat2 = mainStatID.get(tempsub.get(0));
            subStat2Val = ((Integer)tempsub.get(1)+(Integer)tempsub.get(3));
        }

        String subStat3 = "";
        Integer subStat3Val = -1;
        if (subs.length() > 2) {
            tempsub = (JSONArray) subs.get(2);
            subStat3 = mainStatID.get(tempsub.get(0));
            subStat3Val = ((Integer)tempsub.get(1)+(Integer)tempsub.get(3));
        }

        String subStat4 = "";
        Integer subStat4Val = -1;
        if (subs.length() > 3) {
            tempsub = (JSONArray) subs.get(3);
            subStat4 = mainStatID.get(tempsub.get(0));
            subStat4Val = ((Integer)tempsub.get(1)+(Integer)tempsub.get(3));
        }

        tableRuneList.add(new Rune(set,slot,stars,rarity,level,ancient,effCur,effMax,mainStat,
                mainStatVal,innStat,innStatVal,subStat1,subStat1Val,subStat2,
                subStat2Val,subStat3,subStat3Val,subStat4,subStat4Val));
    }

    private static Map<Integer, String> createMainStatMap() {
        Map<Integer,String> myMap = new HashMap<>();
        myMap.put(1, "HP+");
        myMap.put(2, "HP%");
        myMap.put(3, "ATK+");
        myMap.put(4, "ATK%");
        myMap.put(5, "DEF+");
        myMap.put(6, "DEF%");
        myMap.put(8, "SPD");
        myMap.put(9, "CR");
        myMap.put(10, "CD");
        myMap.put(11, "RES");
        myMap.put(12, "ACC");
        return myMap;
    }

    private static Map<Integer, String> createRarityMap() {
        Map<Integer,String> myMap = new HashMap<>();
        myMap.put(1, "Normal");
        myMap.put(2, "Magic");
        myMap.put(3, "Rare");
        myMap.put(4, "Hero");
        myMap.put(5, "Legend");
        return myMap;
    }

    private static Map<Integer, String> createSetIDMap() {
        Map<Integer,String> myMap = new HashMap<>();

        Map<Integer,Map<Integer,Integer>> test = new HashMap<>();
        myMap.put(1, "Energy");
        myMap.put(2, "Guard");
        myMap.put(3, "Swift");
        myMap.put(4, "Blade");
        myMap.put(5, "Rage");
        myMap.put(6, "Focus");
        myMap.put(7, "Endure");
        myMap.put(8, "Fatal");
        myMap.put(10, "Despair");
        myMap.put(11, "Vampire");
        myMap.put(13, "Violent");
        myMap.put(14, "Nemesis");
        myMap.put(15, "Will");
        myMap.put(16, "Shield");
        myMap.put(17, "Revenge");
        myMap.put(18, "Destroy");
        myMap.put(19, "Fight");
        myMap.put(20, "Determination");
        myMap.put(21, "Enhance");
        myMap.put(22, "Accuracy");
        myMap.put(23, "Tolerance");
        return myMap;
    }

    private static ArrayList<Double> calculateEfficiency(JSONObject rune) throws IOException {
        InputStream is = AnalyzeController.class.getResourceAsStream("runeMaxStats.json");
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String jsonContents = s.hasNext() ? s.next() : "";

        JSONObject jo = new JSONObject(jsonContents);
        JSONObject joMain = jo.getJSONObject("mainstat");
        JSONObject joSub = jo.getJSONObject("substat");

        double ratio = 0.0;

        // main stat
        JSONArray mainStat = rune.getJSONArray("pri_eff");
        String mainStatId = String.valueOf(mainStat.getInt(0));
        String runeStars = String.valueOf(rune.getInt("class")%10);

        //System.out.println("mainStatId = " + mainStatId);
        //System.out.println("runeStars = " + runeStars);

        //System.out.println("Max mainstat value for " + runeStars + " stars = " + (double) joMain.getJSONObject(mainStatId).getInt(runeStars));
        //System.out.println("Max mainstat value for 6 stars = " + (double) joMain.getJSONObject(mainStatId).getInt("6"));

        ratio += (double) joMain.getJSONObject(mainStatId).getInt(runeStars) / (double) joMain.getJSONObject(mainStatId).getInt("6");

        //System.out.println("ratio after mainstat = " + ratio);

        //System.out.println("");

        // sub stats
        JSONArray subStats = rune.getJSONArray("sec_eff");
        for (int j = 0; j < subStats.length(); j++) {
            JSONArray tempsub = (JSONArray) subStats.get(j);
            String subStatId = String.valueOf(tempsub.getInt(0));
            //double subStatVal = (tempsub.getInt(1) + tempsub.getInt(3));
            double subStatVal = tempsub.getInt(1) ;

            //System.out.println("subStatId = " + subStatId);

            //System.out.println("subStatVal = " + subStatVal);
            //System.out.println("Max substat value for 6 stars = " + (double) joSub.getJSONObject(subStatId).getInt("6"));

            ratio += subStatVal / ((double) joSub.getJSONObject(subStatId).getInt("6"));

            //System.out.println("ratio after substat " + j + " = " + ratio);

            //System.out.println("");

        }

        // innate stat
        Integer innateStatid = rune.getJSONArray("prefix_eff").getInt(0);
        double innateStatVal = rune.getJSONArray("prefix_eff").getInt(1);
        if (innateStatid != 0) {
            ratio += innateStatVal / (double) joSub.getJSONObject(String.valueOf(innateStatid)).getInt("6");
        }

        double efficiency = (ratio / 2.8) * 100.0;

        DecimalFormat df2 = new DecimalFormat("#.00");
        double effCur = Double.parseDouble(df2.format(efficiency));

        double effMax = (efficiency + ((Math.max(Math.ceil((12 - rune.getInt("upgrade_curr")) / 3.0), 0) * 0.2) / 2.8) * 100);
        effMax = Double.parseDouble(df2.format(effMax));

        //System.out.println(effCur + " | " + effMax);

        ArrayList<Double> effs = new ArrayList<>();
        effs.add(effCur);
        effs.add(effMax);

        return effs;
    }

    public static void autoResizeColumns( TableView<?> table )
    {
        //Set the right policy
        table.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach( (column) ->
        {
            //Minimal width = columnheader
            Text t = new Text( column.getText() );
            double max = t.getLayoutBounds().getWidth();
            for ( int i = 0; i < table.getItems().size(); i++ )
            {
                //cell must not be empty
                if ( column.getCellData( i ) != null )
                {
                    t = new Text( column.getCellData( i ).toString() );
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if ( calcwidth > max )
                    {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-width with some extra space
            column.setPrefWidth( max + 26.0d );
        } );
    }

    public static void autoResizeScene (TableView<?> table) {
        ObservableList<? extends TableColumn<?, ?>> colList = table.getColumns();
        double total = 0.0;
        for (TableColumn col : colList) {
            total += col.getPrefWidth();
        }
        table.getScene().getWindow().setWidth(total+40.0d);
    }

}
