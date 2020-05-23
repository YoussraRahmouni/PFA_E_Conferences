package sample;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class HyperlinkCell implements Callback<TableColumn<Conference, Hyperlink>, TableCell<Conference, Hyperlink>> {
    @Override
    public TableCell<Conference, Hyperlink> call(TableColumn<Conference, Hyperlink> arg) {
        TableCell<Conference, Hyperlink> cell = new TableCell<>() {
            @Override
            protected void updateItem(Hyperlink item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : item);
            }
        };
        return cell;
    }
}
