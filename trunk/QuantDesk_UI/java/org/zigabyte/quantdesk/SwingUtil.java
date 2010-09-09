package org.zigabyte.quantdesk;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Generic <em>Swing</em> utility methods, not specific to the project.
 * This class is a good candidate to move to the QuantDesk Core project.
 * @author Andrey Desyatnikov
 */
public class SwingUtil {
	/** For non-instantiation. */
	private SwingUtil() {
	}

	/**
	 * Creates a sub-menu for changing Look'n'Feel.
	 * @param rootComponent the root component which Look'n'Feel should be changed (child component are processed recursively).
	 * @return a menu item with sub-menu items for each Look'n'Feel
	 * installed in the system with associated actions to change the
	 * Look'n'Feel for the <code>rootComponent</root>.
	 */
	public static JMenu getLafMenu(final Component rootComponent) {
		JMenu jMenu = new JMenu("Look & Feel");
		ButtonGroup buttonGroup = new ButtonGroup();
		final UIManager.LookAndFeelInfo[] installedLFs = UIManager.getInstalledLookAndFeels();
		String currentLF = UIManager.getLookAndFeel().getName();
		for( int i = 0; i < installedLFs.length; i++) {
			JCheckBoxMenuItem jMenuItem = new JCheckBoxMenuItem(installedLFs[i].getName());
			jMenu.add(jMenuItem);
			buttonGroup.add(jMenuItem);
			jMenuItem.setState(currentLF.equals(installedLFs[i].getName()));
			class ChangeLF extends AbstractAction {
				private UIManager.LookAndFeelInfo iLF;
				public ChangeLF(UIManager.LookAndFeelInfo iLF) {
					super(iLF.getName());
					this.iLF = iLF;
				}
				public void actionPerformed(ActionEvent e) {
					try {
						UIManager.setLookAndFeel(iLF.getClassName());
						SwingUtilities.updateComponentTreeUI(rootComponent);
					} catch (Exception ex) {
						System.out.print("Could not set look and feel: " + ex.toString());
					}
				}
			}
			jMenuItem.setAction(new ChangeLF(installedLFs[i]));
		}
		return jMenu;
	}


}
