/*
 * Web application tester- Utility to test web applications via Selenium 
 * Copyright (C) 2021-Nestor Arias
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package oa.com.tests.swing;

import oa.com.tests.actionrunners.exceptions.AbstractException;
import oa.com.tests.Utils;
import oa.com.tests.globals.ActionRunnerManager;
import oa.com.tests.parser.SIDEToTesterParser;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.prefs.Preferences;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import lombok.Data;
import oa.com.tests.actionrunners.interfaces.PluginStoppedListener;
import oa.com.tests.plugins.AbstractDefaultPluginRunner;
import oa.com.utils.Encryption;

//import static javafx.application.Application.launch;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
@Data
public class MainApp extends JFrame {

    private static MainApp instance;
    JTree rootTree = new JTree();
    JComboBox<String> browserTree = new JComboBox<>();
    private JLabel label = new JLabel();
    private ResourceBundle globals = ResourceBundle.getBundle("application");
    private Image errImage, warnImage;
    private Canvas imageContainer;
    private final String NEW_LINE = System.getProperty("line.separator");
    private static final String LOG_NAME = "WebTester.log";
    private static Logger log = Logger.getLogger("WebAppTester");

    public enum PROPS {
        JTREE,
        JCOMBOBOX
    }

    private enum APP_ICON_STATE {
        PLAIN,
        WARN,
        ERROR
    }

    private APP_ICON_STATE appState = APP_ICON_STATE.PLAIN;
    private List<AbstractException> exceptions = new LinkedList<>();

    private MainApp() {
//        setResizable(false);
//File f = new File(".");
//JOptionPane.showMessageDialog(null,"running folder:"+f.getAbsolutePath());
        setIconImage(findImage("/icons/selenium.png"));
        setTitle(globals.getString("app.title"));
        init();
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                ;
            }

            @Override
            public void windowClosing(WindowEvent e) {
                ActionRunnerManager.quit();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
                ;
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                pack();
            }

            @Override
            public void windowActivated(WindowEvent e) {
//                pack();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                ;
            }
        });
    }

    public static Object get(PROPS prop) {
        switch (prop) {
            case JCOMBOBOX:
                return instance.browserTree;
            case JTREE:
            default:
                return instance.rootTree;
        }
    }

    /**
     * Agrega errores a la lista recopilada.
     *
     * @param exceptions
     */
    public static void addException(Exception... exceptions) {
//        instance.exceptions.addAll(Arrays.asList(exceptions)
//                .stream().
//                map(ex -> ex instanceof AbstractException ? (AbstractException) ex
//                : new DefaultException(ex.getMessage())).
//                collect(toList()));
//        instance.appState = APP_ICON_STATE.WARN;
//        instance.label.setText(instance.globals.getString("exec.err.syntaxException.tooltip"));
//        instance.imageContainer.repaint();
//        instance.repaint();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final FileHandler fileHandler;
        try {
            fileHandler = new FileHandler(LOG_NAME);
            fileHandler.setFormatter(new SimpleFormatter());
            log.addHandler(fileHandler);
        } catch (IOException ex) {
            Logger.getLogger("WebAppTester").log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger("WebAppTester").log(Level.SEVERE, null, ex);
        }
        instance = new MainApp();
        instance.pack();
        instance.setVisible(true);
    }

    /**
     * Inicializacion de variables y componentes
     */
    public final void init() {
        //Inicializacion de componentes
        final GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        final Dimension treeDimension = new Dimension(370, 370);
        rootTree.setSize(treeDimension);
        rootTree.setPreferredSize(treeDimension);
        rootTree.setMinimumSize(rootTree.getSize());
        final JScrollPane treeScrollPane = new JScrollPane(rootTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // <editor-fold defaultstate="collapsed" desc="buttons">
        JButton goButton = new JButton(globals.getString("button.runAction")),
                reloadButton = new JButton(globals.getString("button.reloadTree")),
                parseSIButton = new JButton(globals.getString("button.parseSIDE")),
                pwdButton = new JButton(globals.getString("button.buildPWD"));
        parseSIButton.addActionListener(evt -> {
            JFileChooser chooser = new JFileChooser();
            chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                    globals.getString("parser.import.filefilter.description"),
                    "side"));
            chooser.setDialogTitle(globals.getString("parser.import.title"));
            chooser.setAcceptAllFileFilterUsed(true);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.showOpenDialog(this);
            final File SIDEfile = chooser.getSelectedFile();
            if (SIDEfile == null) {
                return;
            }
            File directory = new File("scripts");
            SIDEToTesterParser parser = new SIDEToTesterParser();
            try {
                directory = new File(directory, globals.getString("parser.import.directory"));
                if (!directory.exists()) {
                    directory.mkdir();
                }
                parser.parse(SIDEfile);
                parser.saveAll(directory);
                rootTree.setModel(ActionRunnerManager.getTreeModel());
                repaint();
                JOptionPane.showMessageDialog(this, globals.getString("parser.side.done"));
            } catch (Exception e) {
                addException(e);
            }
        });
        goButton.setSize(new Dimension(90, 26));
        goButton.setPreferredSize(goButton.getSize());
        goButton.addActionListener(this::goAction);
        reloadButton.setSize(goButton.getSize());
        reloadButton.setPreferredSize(reloadButton.getSize());
        reloadButton.addActionListener(evt -> {
            rootTree.setModel(ActionRunnerManager.getTreeModel());
            repaint();
        });

        pwdButton.addActionListener(evt -> {
            try {
                final String unencrypted = JOptionPane.showInputDialog(this,
                        globals.getString("button.buildPWD.message"),
                        globals.getString("button.buildPWD"),
                        JOptionPane.INFORMATION_MESSAGE);
                if (unencrypted == null) {
                    return;
                }
                final String encrypted = Encryption.encrypt(unencrypted);
                JOptionPane.showInputDialog(this,
                        globals.getString("button.buildPWD.message.encrypted"),
                        encrypted);
            } catch (Exception ex) {
                log.log(Level.SEVERE, null, ex);
            }
        });

        JTabbedPane tabs = new JTabbedPane();
        final JPanel buttonsPanel = new JPanel(new GridBagLayout());
        tabs.add(buttonsPanel);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonsPanel.add(goButton, gbc);
        gbc.gridx += gbc.gridwidth;
        buttonsPanel.add(reloadButton, gbc);
        gbc.gridx += gbc.gridwidth;
        buttonsPanel.add(browserTree, gbc);
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy++;
        buttonsPanel.add(parseSIButton, gbc);
        gbc.gridx += gbc.gridwidth;
        buttonsPanel.add(pwdButton, gbc);
        tabs.add(globals.getString("window.tools.panel"), buttonsPanel);

        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Label and icon">
        label.setPreferredSize(new Dimension(330, 26));
        label.setSize(label.getPreferredSize());
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (exceptions.isEmpty()) {
                    return;
                }
                final String ERR_TITLE = globals.getString("globals.error.title");
                final String lineSep = System.getProperty("line.separator");

                String msg = exceptions.stream()
                        .map(ae -> {
                            final String context = ae.getContext();
                            return (context == null ? "" : context + NEW_LINE)
                                    + Utils.getFirstLine(ae);
                        })
                        .collect(joining(lineSep));
                if (msg.trim().isEmpty()) {
                    return;
                }
                final JTextArea ta = new JTextArea(msg);
                ta.setEditable(false);
                final Dimension preferredSize = new Dimension(500, 400);
                ta.setSize(preferredSize);
                ta.setPreferredSize(preferredSize);
                final JScrollPane taPane = new JScrollPane(ta,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//                final int BORDER = 20;
//                taPane.setPreferredSize(new Dimension(preferredSize.width + BORDER, preferredSize.height + BORDER));
//                taPane.add(ta);
                JOptionPane.showMessageDialog(null, taPane, ERR_TITLE, JOptionPane.ERROR_MESSAGE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        imageContainer = new Canvas() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                switch (appState) {
                    case ERROR:
                        g.drawImage(errImage, 0, 0, null);
                        break;
                    case WARN:
                        g.drawImage(warnImage, 0, 0, null);
                        break;
                }
            }
        };
        imageContainer.setPreferredSize(new Dimension(20, 20));
        imageContainer.setMinimumSize(imageContainer.getPreferredSize());
        errImage = findImage("/icons/error.png");
        warnImage = findImage("/icons/warning.png");
        //</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Browser drop-down list">
        for (String item : Arrays.asList(ActionRunnerManager.BROWSERTYPE.values())
                .stream()
                .map(bn -> globals.getString("settings.driver." + bn.name() + ".name"))
                .collect(toList())) {
            browserTree.addItem(item);
        }
        browserTree.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateBrowser(true);
            }
        });
        final String storedBrowserName = Preferences.userRoot().get("webapptester.browser", null);
        if (storedBrowserName != null) {
            final ActionRunnerManager.BROWSERTYPE storedBrowser
                    = ActionRunnerManager.BROWSERTYPE.valueOf(storedBrowserName);
            ActionRunnerManager.set(storedBrowser);
            browserTree.setSelectedItem(globals.getString("settings.driver." + storedBrowserName + ".name"));
        }
        updateBrowser(false);
        //</editor-fold>

        rootTree.setModel(ActionRunnerManager.getTreeModel());
        // <editor-fold defaultstate="collapsed" desc="Space for plugins">
        final List<AbstractDefaultPluginRunner> plugins = ActionRunnerManager.getPluginsSt();
        boolean hasPlugins = !plugins.isEmpty();
        int addedPlugins = 0;
        JPanel pluginGrid = new JPanel(new GridBagLayout());
        if (hasPlugins) {
            gbc.gridx = gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;
            for (AbstractDefaultPluginRunner plugin : plugins) {
                JButton pluginBtn;
                try {
                    pluginBtn = new JButton(plugin.getButtonActionCommand(), plugin.getIcon());
                    pluginBtn.addActionListener(plugin.getActionListener());
                    pluginGrid.add(pluginBtn, gbc);
                    pluginBtn.setPreferredSize(new Dimension(190, 32));
                    gbc.gridx++;
                    if (gbc.gridx == 2) {
                        gbc.gridx = 0;
                        gbc.gridy++;
                    }
                    pluginBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            pluginBtn.setEnabled(false);
                            ActionRunnerManager.registerPluginListenerSt(plugin, new PluginStoppedListener() {
                                @Override
                                public void unregistered(AbstractDefaultPluginRunner plugin) {
                                    pluginBtn.setEnabled(true);
                                }
                            });
                        }
                    });
                    addedPlugins++;
                } catch (IOException ex) {
                    log.log(Level.SEVERE, null, ex);
                }
            }

            tabs.add(globals.getString("window.plugins.panel"), pluginGrid);
        }
        //</editor-fold>

//Distrubucion grafica
        setLayout(layout);
        gbc.gridwidth = 6;
        gbc.gridx = gbc.gridy = 0;
        getContentPane().add(treeScrollPane, gbc);

        gbc.gridwidth = 6;
        gbc.gridheight = 3;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(tabs, gbc);

        gbc.gridy += gbc.gridheight;
        gbc.gridheight = 1;
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(label, gbc);
        gbc.gridwidth = 2;
        gbc.gridx += gbc.gridwidth;
        getContentPane().add(imageContainer, gbc);
    }

    /**
     * Cambia el tipo de navegador sin invocar auditores de eventos
     *
     * @param type
     */
    public static void setBrowser(ActionRunnerManager.BROWSERTYPE type) {
        if (instance == null) {
            return;
        }
        final ItemListener[] itemListeners = instance.browserTree.getItemListeners();
        for (ItemListener listener : itemListeners) {
            instance.browserTree.removeItemListener(listener);
        }
        String key = "settings.driver." + type.name() + ".name";
        final String option = instance.globals.getString(key);
        instance.browserTree.setSelectedItem(option);
        for (ItemListener listener : itemListeners) {
            instance.browserTree.addItemListener(listener);
        }
    }

    /**
     * Metodo para el boton "Vamos
     *
     * @param e
     */
    public void goAction(ActionEvent e) {
        final String ERR_TITLE = globals.getString("globals.error.title");
        //No selecciono navegador?
        if (browserTree.getSelectedIndex() == -1) {
            String message = globals.getString("settings.driver.emptyException");
            JOptionPane.showMessageDialog(null, message, ERR_TITLE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ActionRunnerManager.exec(rootTree.getSelectionModel().getSelectionPath(), log);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.toString(), ERR_TITLE, JOptionPane.ERROR_MESSAGE);
                    log.log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }

    /**
     * Actualiza el navegador a partir del estado del combo de navegadores
     *
     * @param withUsrMsg
     * @return El tipo de navegador seleccionado, o null si ninguno lo fue.
     */
    private ActionRunnerManager.BROWSERTYPE updateBrowser(boolean withUsrMsg) throws HeadlessException {
        final String item = (String) browserTree.getSelectedItem();
        if (item == null) {
            return null;
        }
        final Optional<ActionRunnerManager.BROWSERTYPE> browserMatch = Arrays.asList(ActionRunnerManager.BROWSERTYPE.values())
                .parallelStream()
                .filter(bn -> item.equals(globals.getString("settings.driver." + bn.name() + ".name")))
                .findFirst();
        if (browserMatch.isPresent()) {
            final ActionRunnerManager.BROWSERTYPE resp = browserMatch.get();
            ActionRunnerManager.set(resp);
            Preferences.userRoot().put("webapptester.browser", resp.name());
            return resp;
        } else {
            if (withUsrMsg) {
                String msg = globals.getString("settings.driver.emptyException");
                JOptionPane.showMessageDialog(null, msg, globals.getString("globals.error.title"), JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    public final Image findImage(final String iconPath) {
        try {
            Image img = ImageIO.read(getClass().getResource(iconPath));
            return img;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MainApp getInstance() {
        return instance;
    }

    /**
     * Retorna el log utilizado por la aplicación
     *
     * @return
     */
    public static Logger getLog() {
        return log;
    }

}
