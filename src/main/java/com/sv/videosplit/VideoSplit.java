package com.sv.videosplit;

import com.sv.core.Utils;
import com.sv.core.config.DefaultConfigs;
import com.sv.core.logger.MyLogger;
import com.sv.swingui.component.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;
import java.util.Timer;

import static com.sv.core.Constants.EMPTY;
import static com.sv.swingui.UIConstants.EMPTY_BORDER;

/**
 * Created by IntelliJ IDEA.
 * User: Shailendra Verma (shailendravermag@gmail.com)
 * Date: Aug 28, 2006
 * Time: 3:30:57 PM
 * <p>
 * Change file names performs different operations
 * on file names by renaming them.
 * <p>
 * View usage for help.
 */
public class VideoSplit extends AppFrame {

    enum Configs {
        LastFile, LastSplitChoice, TimeSplitStart, TimeSplitEnd,
        SplitSizeValInMB;
    }

    private final MyLogger logger;
    private final DefaultConfigs configs;

    private JLabel lblFileToSplit, lblSplitOption, lblLoading, lblTimeSpent;
    private JTextField txtFile;
    private JComboBox<SplitInfo> jcb;
    private JTextArea taStatus;
    private JButton btnSplit, btnBrowse, btnClear, btnUsage, btnExit;
    private JPanel jpSouth;
    private JPanel jpNorth;
    private JPanel jpAllOptions, jpBySize, jpByTime, jpCutByTime;

    // CutByTime
    private JLabel lblCBTStartTime, lblCBTEndTime;
    private JFormattedTextField txtCBTStartTime, txtCBTEndTime;

    private VideoSplit() {
        super("Split Video");
        logger = MyLogger.createLogger("split-video.log");
        configs = new DefaultConfigs(logger, Utils.getConfigsAsArr(Configs.class));
        initComponents();

        String inputPath = "C:\\Users\\Shailendra\\Downloads\\De_De_Pyaar_De_2019_Hindi_Full_Movie.mp4";
        String outputPath = "C:\\Users\\Shailendra\\Downloads\\De_De_Pyaar_De_2019_Hindi_Full_Movie-test.mp4";

/*
        // Split a 10-second segment starting from 00:00:05
        FFmpeg.atPath(Paths.get("C:\\Users\\Shailendra\\Downloads\\ffmpeg-2026-03-18-git-106616f13d-essentials_build\\bin"))
                .addInput(UrlInput.fromUrl(inputPath)
                        //.setPosition(0, TimeUnit.HOURS) // Start time
                        .setPosition(0, TimeUnit.HOURS) // Start time
                        .setDuration(1, TimeUnit.HOURS)) // Duration of the clip
                .addOutput(UrlOutput.toPath(Paths.get(outputPath))
                        //.setVideoCodec("copy") // Sets -c:v copy
                        //.setAudioCodec("copy") // Sets -c:a copy
                        .copyAllCodecs()) // Fast split without re-encoding
                .execute();
*/
    }

    private void initComponents() {

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitApp();
            }
        });

        UIName uin = UIName.LBL_FILE_TO_SPLIT;
        lblFileToSplit = new AppLabel(uin.name, txtFile, uin.mnemonic, uin.tip);
        uin = UIName.BTN_CHNG;
        lblSplitOption = new AppLabel(uin.name, txtFile, uin.mnemonic, uin.tip);
        lblLoading = new JLabel(new ImageIcon("./icons/loading.gif"));
        lblTimeSpent = new JLabel("");

        txtFile = new AppTextField(configs.getConfig(Configs.LastFile.name()), 30);

        MaskFormatter timeMask = null;
        try {
            timeMask = new MaskFormatter("##:##:##");
        } catch (ParseException e) {
            throw new VideoSplitException(e.getMessage());
        }
        timeMask.setPlaceholderCharacter('-');

        uin = UIName.LBL_START_TIME;
        lblCBTStartTime = new AppLabel(uin.name, txtFile, uin.mnemonic, uin.tip);
        uin = UIName.LBL_END_TIME;
        lblCBTEndTime = new AppLabel(uin.name, txtFile, uin.mnemonic, uin.tip);

        txtCBTStartTime = new JFormattedTextField(timeMask);
        txtCBTStartTime.setColumns(5);
        txtCBTStartTime.setText(configs.getConfig(Configs.TimeSplitStart.name()));
        txtCBTEndTime = new JFormattedTextField(timeMask);
        txtCBTEndTime.setText(configs.getConfig(Configs.TimeSplitEnd.name()));
        txtCBTEndTime.setColumns(5);

        SplitInfo[] allChoices = SplitInfo.values();
        Arrays.sort(allChoices, new SplitComparator());
        jcb = new JComboBox<>(allChoices); //drop down Options
        String actionConfig = configs.getConfig(Configs.LastSplitChoice.name());
        if (isValidAction(actionConfig)) {
            jcb.setSelectedItem(SplitInfo.valueOf(actionConfig));
        }

        taStatus = new JTextArea(EMPTY);
        taStatus.setLineWrap(true);
        taStatus.setAutoscrolls(true);

        uin = UIName.BTN_CHNG;
        btnSplit = new AppButton(uin.name, uin.mnemonic, uin.tip);
        uin = UIName.BTN_BROWSE;
        btnBrowse = new AppButton(uin.name, uin.mnemonic, uin.tip);
        uin = UIName.BTN_CLEAR;
        btnClear = new AppButton(uin.name, uin.mnemonic, uin.tip);
        uin = UIName.BTN_USG;
        btnUsage = new AppButton(uin.name, uin.mnemonic, uin.tip);
        btnExit = new AppExitButton(true);

        drawUI();
        addActions();

        printUsage();
        showRelevantOptions();
        //setSize(new Dimension(1200, 500));
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
        setControlsToEnable();
    }

    private boolean isValidAction(String config) {
        try {
            SplitInfo.valueOf(config);
            return true;
        } catch (IllegalArgumentException e) {
            // no action
        }
        return false;
    }

    private void addActions() {
        btnBrowse.addActionListener(event -> btnBrowseAction());
        btnSplit.addActionListener(event -> btnChangeAction());
        btnExit.addActionListener(event -> exitApp());
        btnUsage.addActionListener(event -> printUsage());
        jcb.addActionListener(event -> showRelevantOptions());
        btnClear.addActionListener(event -> taStatus.setText(EMPTY));
    }

    private void btnBrowseAction() {
        JFileChooser jfc = new JFileChooser(txtFile.getText(), FileSystemView.getFileSystemView());
        // 2. Define the filter for video extensions
        FileNameExtensionFilter videoFilter = new FileNameExtensionFilter(
                "Video Files", "mp4", "avi", "mkv", "mov", "wmv");
        jfc.setFileFilter(videoFilter);
        jfc.setAcceptAllFileFilterUsed(true);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.showDialog(this, "Select");
        try {
            if (jfc.getSelectedFile() != null) {
                txtFile.setText(jfc.getSelectedFile().getCanonicalPath());
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private void btnChangeAction() {
        taStatus.setText(EMPTY);
        handleControls(false);
        startAsyncProcessing();
    }

    private void startAsyncProcessing() {
        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    startProcessing(prepareArguments());
                } catch (Exception e) {
                    printMsg(e.getMessage(), true);
                    logger.error(e);
                }
                return null;
            }
        };
        mySwingWorker.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("state")) {
                if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                    handleControls(true);
                }
            }
        });
        mySwingWorker.execute();
        /*synchronized (mySwingWorker) {
            try {
                mySwingWorker.wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }*/
    }

    private Arguments prepareArguments() {

        Arguments arguments = new Arguments();
        arguments.setFileName(txtFile.getText());
        arguments.setSplitInfo(((SplitInfo) (jcb.getSelectedItem())));
        arguments.setSplitBy(((SplitInfo) (jcb.getSelectedItem())).getLabel());
        arguments.setStartTime(txtCBTStartTime.getText());
        arguments.setEndTime(txtCBTEndTime.getText());
        //arguments.setSplitSizeInMB();

        printMsg("prepareArguments: " + arguments);
        return arguments;
    }

    /**
     * Exit the Application
     */
    private void exitApp() {
        configs.saveConfig(this);
        setVisible(false);
        dispose();
        logger.dispose();
        System.exit(0);
    }

    private void drawUI() {
        getContentPane().setLayout(new BorderLayout());

        JPanel jpUp = new JPanel();
        JPanel jpDown = new JPanel();
        JPanel jpMid = new JPanel();
        jpSouth = new JPanel();
        jpNorth = new JPanel();
        jpNorth.setLayout(new GridLayout(2, 1));

        jpAllOptions = new JPanel();
        jpBySize = new JPanel();
        jpByTime = new JPanel();
        jpCutByTime = new JPanel();
        jpAllOptions.add(jpBySize);
        jpAllOptions.add(jpByTime);
        jpAllOptions.add(jpCutByTime);

        jpCutByTime.add(lblCBTStartTime);
        jpCutByTime.add(txtCBTStartTime);
        jpCutByTime.add(lblCBTEndTime);
        jpCutByTime.add(txtCBTEndTime);

        jpByTime.add(new JLabel("Time TBD"));
        jpBySize.add(new JLabel("Size TBD"));

        jpUp.add(lblFileToSplit);
        jpUp.add(txtFile);
        jpUp.add(btnBrowse);
        jpUp.add(lblSplitOption);
        jpUp.add(jcb);
        jpUp.add(jpAllOptions);
        jpDown.add(jpMid);
        jpDown.add(btnSplit);
        jpDown.add(btnUsage);
        jpDown.add(btnExit);
        jpDown.add(lblLoading);
        jpDown.add(lblTimeSpent);
        lblLoading.setVisible(false);
        lblTimeSpent.setVisible(false);
        jpSouth.add(btnClear);

        jpNorth.add(jpUp);
        jpNorth.add(jpDown);

        add(jpNorth, BorderLayout.NORTH);
        add(jpSouth, BorderLayout.SOUTH);
        JScrollPane jsp = new JScrollPane(taStatus);
        jsp.setBorder(EMPTY_BORDER);
        add(jsp, BorderLayout.CENTER);
    }

    /**
     * Operates on file names after accessing
     * and tries to ignore errors up to maximum
     * extends.
     *
     * @param args parameters for processing filenames
     */
    private void process(Arguments args) {
        final String log = "process: ";

        long startTime = Utils.getNowMillis();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    lblTimeSpent.setText(Utils.getTimeDiffSecStr(startTime));
                });
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 1000);
        printMsg(log + "Starting process for [" + args.getSplitBy() + "] at [" + Utils.getFormattedDate() + "]");

        if (!args.getFileName().isEmpty()) {
            BaseProcessor processor = getProcessor(args);

            if (processor == null) {
                printMsg(log + "Unable to find processor", true);
                return;
            }

            File file = Utils.createPath(args.getFileName()).toFile();
            if (file.isFile()) {
                updateTitle(processor.execute(args) ? "Done" : "Failed");
                printMsg(log + "Ending process for [" + args.getSplitBy() + "] in " + Utils.getTimeDiffSecStr(startTime));
                timer.cancel();
            } else {
                printMsg("Not a valid file.", true);
            }
        } else {
            printMsg("No file to process.", true);
        }
    }

    //TODO: parameter to show only actual results

    private BaseProcessor getProcessor(Arguments args) {
        final String log = "getProcessor: ";
        final String clazzName = this.getClass().getPackage().getName() + ".processors." + args.getSplitInfo().getValue();
        logger.info(log + "Trying to initialize processor: " + clazzName);
        try {
            return (BaseProcessor) Class.forName(clazzName)
                    .getConstructor(logger.getClass())
                    .newInstance(logger);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 InstantiationException e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Prints message on console and/or text area
     *
     * @param s message to print
     */
    private void printMsg(String s) {
        printMsg(s, false);
    }

    private void printMsg(String s, boolean error) {
        taStatus.append(s + System.lineSeparator());
        s = "[" + new Date() + "]" + s;
        printLog(s, error);
    }

    private void printLog(String s, boolean error) {
        if (error) {
            logger.error(s);
        } else {
            logger.info(s);
        }
    }

    /**
     * Validate all the arguments.
     *
     * @param args arguments
     * @return boolean as status
     */
    private boolean validateAllArgs(Arguments args) {
        return (validateArgs(args) && validateFile(args.getFileName()));
    }

    /**
     * Validate the arguments against not null condition.
     *
     * @param args parameters for processing filenames
     * @return boolean status of operation
     */
    private boolean validateArgs(Arguments args) {
        return (
                Utils.hasValue(args.getFileName()) ||
                        args.getSplitInfo() != null);
    }

    /**
     * checks if passed dir name exists and accessible
     *
     * @param path file name
     * @return boolean status of operation
     */
    private boolean validateFile(String path) {
        File file = new File(path);
        boolean result = file.exists() && file.isFile();
        if (!result) {
            logger.error("Either path for file [" + path + "] does not exists or it is not a file.");
        }
        return result;
    }

    private void showRelevantOptions() {
        for (Component c : jpAllOptions.getComponents()) {
            c.setVisible(false);
        }

        try {
            JPanel jp = (JPanel) this.getClass().getDeclaredField(((SplitInfo) (jcb.getSelectedItem())).getPanelName()).get(this);
            jp.setVisible(true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new VideoSplitException(e.getMessage());
        }
    }

    /**
     * print the usage of class
     */
    private void printUsage() {
        String usage = "javac VideoSplit <srcFile> <option> [<extra-param>]"
                + "\nwhere"
                + "\n <srcFile> - Video file to be processed."
                + "\n     1.  CutByTime - This will cut clip from main video based on start and end time.  Save clip at the source location with cut as suffix"
                + "\n     2.  SplitByTime - TBD"
                + "\n     3.  SplitBySize - TBD"
                + "\n example 1. java VideoSplit c:/test/Test.mp4 CutByTime 00:00:00 01:00:00";

        taStatus.setText("Usage = " + usage);
    }

    /**
     * This method provides support to execute it from command line.
     * As of thought removing this support from command line on 16-Oct-2017
     *
     * @param args of type {@link Arguments}
     */
    private void startProcessing(Arguments args) {
        if (args != null && validateAllArgs(args)) {
            process(args);
        } else {
            printMsg("Unable to start process.", true);
        }
    }

    private void setControlsToEnable() {
        Component[] components = {
                jpNorth, jpSouth
        };
        setComponentToEnable(components);
        enableControls();
    }

    private void handleControls(boolean enable) {
        showLoading(enable);
        if (enable) {
            enableControls();
        } else {
            disableControls();
        }
    }

    private void showLoading(boolean enable) {
        lblLoading.setVisible(!enable);
        lblTimeSpent.setVisible(!enable);
    }

    public String getFolder() {
        return txtFile.getText();
    }

    public String getAction() {
        return ((SplitInfo) (jcb.getSelectedItem())).name();
    }

    /**
     * entry unit point
     *
     * @param args parameters
     */
    public static void main(String[] args) {
        new VideoSplit();
    }

    public String getLastFile() {
        return txtFile.getText();
    }

    public String getLastSplitChoice() {
        return ((SplitInfo) (jcb.getSelectedItem())).name();
    }

    public String getTimeSplitStart() {
        return txtCBTStartTime.getText();
    }

    public String getTimeSplitEnd() {
        return txtCBTEndTime.getText();
    }

    public String getSplitSizeValInMB() {
        return txtFile.getText();
    }
}
