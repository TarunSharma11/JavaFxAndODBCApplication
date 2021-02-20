package javafxapplication2;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Window;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import java.sql.*;
import java.util.*;
import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;
public class JavaFXApplication2 extends Application {

 public void start(Stage s)
 {
 s.setTitle("Check your symptoms");
 Menu m = new Menu("Edit");
 Menu mn = new Menu("Help");

 MenuItem m1 = new MenuItem("Reset");
 MenuItem m2 = new MenuItem("About");
 m.getItems().add(m1);
 mn.getItems().add(m2);

 Label l = new Label();

 EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
 public void handle(ActionEvent e)
 {
 l.setText("******************It is an app for predicting yourdisease*******************\n\n");
 }
 };
 m2.setOnAction(event);
 MenuBar mb = new MenuBar();
 mb.getMenus().add(m);
 mb.getMenus().add(mn);
 VBox vb = new VBox(mb, l);

Text text1 = new Text("Name*");
 Text text2 = new Text("Email");
 Text text3=new Text("DOB");

 TextField textField1 = new TextField();

 
 TextField textField2 = new TextField();
 DatePicker db = new DatePicker();

 Button button1 = new Button("Submit");

 Label lb2 = new Label("What are your symptoms: ");

 CheckBox c1 = new CheckBox("Fever");
 CheckBox c2 = new CheckBox("Skin Blister");
 CheckBox c3 = new CheckBox("Fatigue");
 CheckBox c4 = new CheckBox("Chills");
 CheckBox c5 = new CheckBox("Sweating");
 CheckBox c6 = new CheckBox("Running Nose");
 CheckBox c7 = new CheckBox("Cough");
 CheckBox c8 = new CheckBox("Headache");

 EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
 public void handle(ActionEvent e)
 {
 c1.setSelected(false);
 c2.setSelected(false);
 c3.setSelected(false);
 c4.setSelected(false);
 c5.setSelected(false);
 c6.setSelected(false);
 c7.setSelected(false);
 c8.setSelected(false);
 textField1.setText("");
 textField2.setText("");
 db.setValue(null);
 }
 };

 m1.setOnAction(event1);

 GridPane gridPane = new GridPane();

 gridPane.setMinSize(400, 200);

 gridPane.setPadding(new Insets(10, 10, 10, 10));

 gridPane.setVgap(5);
 gridPane.setHgap(5);

 gridPane.setAlignment(Pos.CENTER);

 gridPane.add(text1, 0, 0);
 gridPane.add(textField1, 1, 0);
 gridPane.add(text2, 0, 1);
 gridPane.add(textField2, 1, 1);
 gridPane.add(text3, 0, 2);
 gridPane.add(db, 1, 2);

 gridPane.add(lb2, 0, 6);
 gridPane.add(c1, 0, 9);
 gridPane.add(c2, 1, 9);
 gridPane.add(c3, 0, 10);
 gridPane.add(c4, 1, 10);
 gridPane.add(c5, 0, 11);
 gridPane.add(c6, 1, 11);
 gridPane.add(c7, 0, 12);
 gridPane.add(c8, 1, 12);
 gridPane.add(button1, 0, 20);

 button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

 text1.setStyle("-fx-font: normal bold 20px 'serif' ");
 text2.setStyle("-fx-font: normal bold 20px 'serif' ");
 text3.setStyle("-fx-font: normal bold 20px 'serif' ");
 l.setTextFill(Color.WHITE);
 l.setStyle("-fx-font: normal bold 20px 'serif'");
 gridPane.setStyle("-fx-background-color: BEIGE;");





 VBox rootPane = new VBox();
 rootPane.setStyle("-fx-background-color: PURPLE;");
 rootPane.getChildren().addAll(vb,gridPane);

 button1.setOnAction(new EventHandler<ActionEvent>() {
 @Override
 public void handle(ActionEvent event) {
 if(textField1.getText().isEmpty()) {
 showAlert(Alert.AlertType.ERROR, rootPane.getScene().getWindow(),
 "Form Error!", "Please enter your name");
 return;
 }
 else if(!c1.isSelected() && !c2.isSelected() && !c3.isSelected() && !c4.isSelected() &&
!c5.isSelected() && !c6.isSelected() && !c7.isSelected() && !c8.isSelected())
 {
 showAlert(Alert.AlertType.ERROR, rootPane.getScene().getWindow(),
 "Form Error!", "Please select any one symptom");
 return;
 }
 else
 {
 String name=textField1.getText();
 String sym="";
 if(c1.isSelected())
 sym+="Fever,";
 if(c2.isSelected())
 sym+="Skin Blister,";
 if(c3.isSelected())
 sym+="Fatigue,";
 if(c4.isSelected())
 sym+="Chills,";
 if(c5.isSelected())
 sym+="Sweating,";
 if(c6.isSelected())
 sym+="Running Nose,";
 if(c7.isSelected())
 sym+="Cough,";
 if(c8.isSelected())
 sym+="Headache,";
 sym=sym.substring(0,sym.length()-1);
 String dis=getDisease(sym);
 showAlert(Alert.AlertType.INFORMATION, rootPane.getScene().getWindow(),
 "Prediction", name+",You have "+dis);
 return;
 }
}});
 Scene sc = new Scene(rootPane, 700, 500);
 s.setScene(sc);
 s.show();
 }
 private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
 Alert alert = new Alert(alertType);
 alert.setTitle(title);
 alert.setHeaderText(null);
 alert.setContentText(message);
 alert.initOwner(owner);
 alert.show();
}
 public String getDisease(String arg)
{
int count=0;
String k[]=arg.split(",");
String s[][]=new String[100][];
String diseases[]=new String[100];
try{ Class.forName("oracle.jdbc.driver.OracleDriver");
Connection
con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","ROOT");
PreparedStatement ps=con.prepareStatement( "select * from dtable");
int ip=ps.executeUpdate();
ResultSet rs=ps.executeQuery();
ResultSetMetaData rsmd=rs.getMetaData();
Set<String> ss=new HashSet<String>();
while(rs.next())
{
diseases[count]=rs.getString(1);
s[count++]=rs.getString(2).split(",");
for(int j=0;j<s[count-1].length;j++)
ss.add(s[count-1][j]);
}
int nu=ss.size();
int l=0;
String symptoms[]=new String[nu];
for(String sto : ss){symptoms[l++]=sto;}
Map<String,Integer> mp=new LinkedHashMap<String,Integer>();
for(int i=0;i<symptoms.length;i++)
mp.put(symptoms[i],i);
int st[][]=new int[count][symptoms.length];
for(int i=0;i<count;i++)
{
for(int j=0;j<s[i].length;j++)
st[i][mp.get(s[i][j])]++;
}
int inp[]=new int[symptoms.length];
for(int j=0;j<k.length;j++)
inp[mp.get(k[j])]++;
double result[]= euclid(st,inp,count,symptoms.length);
Map<String,Double> tmp=new HashMap<String,Double>();
for(int i=0;i<count;i++)
tmp.put(diseases[i],result[i]);
Map<String, Double> sorted = tmp
.entrySet()
.stream()
.sorted(comparingByValue())
.collect(
toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
LinkedHashMap::new));
String d=sorted.entrySet().iterator().next().getKey();
return d;
}
catch (Exception e2)
{
System.out.println(e2);
return "error";
}
}
public double[] euclid(int st[][],int inp[],int count,int k)
{
double result[]=new double[k];
for(int i=0;i<count;i++)
{
double temp=0.0;
for(int j=0;j<k;j++)
temp+=Math.pow(st[i][j]-inp[j],2);
result[i]=Math.sqrt(temp);
}
return result;
}
 public static void main(String args[])
 {
 launch(args);
 }
}