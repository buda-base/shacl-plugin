package at.ac.tuwien.shacl.plugin;

import at.ac.tuwien.shacl.plugin.events.ShaclValidationRegistry;
import at.ac.tuwien.shacl.plugin.ui.ShaclConstraintViolationPanel;
import at.ac.tuwien.shacl.plugin.util.TestUtil;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * Tests the constraint violation panel.
 */
public class TestConstraintViolationPanel {
    /**
     * Test, if table is indeed updated with new data after a SHACL validation.
     *
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void testTableUpdateAfterValidation() throws FileNotFoundException, InterruptedException {
        ShaclConstraintViolationPanel panel = new ShaclConstraintViolationPanel(null);
        assertEquals(0, panel.getTableModel().getRowCount());

        ShaclValidationRegistry.getValidator().runValidation(ModelFactory.createDefaultModel(), TestUtil.getShapesAndDataModel());

        assertEquals(2, panel.getTableModel().getRowCount());
        assertNotNull(panel.getTableModel().getValueAt(0, 8));
        assertNotNull(panel.getTableModel().getValueAt(1, 8));
    }

    @Test
    public void testBehaviorAfterInit() throws FileNotFoundException, InterruptedException {
        ShaclConstraintViolationPanel panel = new ShaclConstraintViolationPanel(null);

        ShaclValidationRegistry.getValidator().runValidation(ModelFactory.createDefaultModel(), TestUtil.getShapesAndDataModel());

        assertFalse(panel.getTable().isCellEditable(1, 8));
    }

    @Test
    public void testDispose() throws FileNotFoundException, InterruptedException {
        ShaclConstraintViolationPanel panel = new ShaclConstraintViolationPanel(null);
        ShaclValidationRegistry.getValidator().runValidation(ModelFactory.createDefaultModel(), TestUtil.getShapesAndDataModel());
        assertEquals(2, panel.getTableModel().getRowCount());

        panel.getTableModel().setRowCount(0);
        panel.dispose();
        ShaclValidationRegistry.getValidator().runValidation(ModelFactory.createDefaultModel(), TestUtil.getShapesAndDataModel());

        assertEquals(0, panel.getTableModel().getRowCount());
    }
}
