package GUI;

import Functionality.BlueprintSaves.SaveController;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

public class DropBlueprintListener implements DropTargetListener {

    private Frame parent;

    public DropBlueprintListener(Frame parent) {
        this.parent = parent;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        parent.setDropHover(true);
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {

    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        parent.setDropHover(false);
    }

    @Override
    public void drop(DropTargetDropEvent event) {
        event.acceptDrop(DnDConstants.ACTION_COPY);

        Transferable transferable = event.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        for (DataFlavor flavor : flavors) {
            try {
                if (flavor.isFlavorJavaFileListType()) {

                    List files = (List) transferable.getTransferData(flavor);

                    for (Object file : files) {
                        SaveController.openBlueprint(((File) file).getPath());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        event.dropComplete(true);
        parent.setDropHover(false);
    }

}
