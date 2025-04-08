package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.wedding.Wedding;

/**
 * Panel containing the list of weddings.
 */
public class WeddingListPanel extends UiPart<Region> {
    private static final String FXML = "WeddingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WeddingListPanel.class);

    @FXML
    private ListView<Wedding> weddingListView;

    /**
     * Creates a {@Code WeddingListPanel} with the given {@code ObservableList}.
     * @param weddingList the weddingList to be displayed
     */
    public WeddingListPanel(ObservableList<Wedding> weddingList) {
        super(FXML);
        weddingListView.setItems(weddingList);
        weddingListView.setCellFactory(listView -> new WeddingListViewCell());
    }

    class WeddingListViewCell extends ListCell<Wedding> {
        @Override
        protected void updateItem(Wedding wedding, boolean empty) {
            super.updateItem(wedding, empty);

            if (empty || wedding == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WeddingCard(wedding, getIndex() + 1).getRoot());
            }
        }
    }

    /*
     * Highlights the person
     */
    public void selectWedding(Wedding wedding) {
        weddingListView.getSelectionModel().select(wedding);
    }
}
