package major_project.view.DialogGuis;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class IntInput {
    public static int getTextInput(String title, String header, String contextText){
        TextInputDialog box = new TextInputDialog();
        box.setHeaderText(header);
        box.setContentText(contextText);
        Optional<String> results = box.showAndWait();
        if(results.isPresent()){
            try{
                return Integer.parseInt(results.get());
            } catch (NumberFormatException e){
                if(ConfirmationDialog.buildConfirmationDialog("Invalid Input", "Input Entered Was Not Valid", "Would you like to try again?")){
                    return getTextInput(title, header,contextText);

                }
            }
        }
        return 0; //should never get here
    }
}
