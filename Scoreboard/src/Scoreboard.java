import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Scoreboard {

    private int scoreHome = 0;
        int previousScoreHome = 0;
    private int scoreAway = 0;
        int previousScoreAway = 0;
        
    private String actualQuarter = "Q1";
    
    private File fileScoreHome = null;
    private File fileScoreAway = null;
    private File fileQuarter = null;
    
    private boolean isTDHome = false; private boolean isTDAway = false;
    
    private JFrame f = null;
    private JPanel home = null; private JPanel away = null;
    private JLabel homeTextArea = null; private JLabel awayTextArea = null;
    private JLabel homeScoreArea = null; private JLabel awayScoreArea = null;
    private JLabel homeTextScore = null; private JLabel awayTextScore = null;
    private JButton homeTD = null; private JButton awayTD = null;
    private JButton homeTransfo1 = null; private JButton awayTransfo1 = null;
    private JButton homeTransfo2 = null; private JButton awayTransfo2 = null;
    private JButton homeFG = null; private JButton awayFG = null;
    private JButton homeSafety = null; private JButton awaySafety = null;
    private JButton homeReset = null; private JButton awayReset = null;
    private JLabel homeGetFileTextArea = null; private JLabel awayGetFileTextArea = null; private JLabel quarterGetFileTextArea = null;
    private JTextField homeFilePath = null; private JTextField awayFilePath = null; private JTextField quarterFilePath = null; 
    private JLabel timeText = null;
    private JButton q1 = null; private JButton q2 = null; private JButton q3 = null; private JButton q4 = null;
    
    public Scoreboard() {

    	f = new JFrame("Scoreboard");
    	initiateWindow();
    	
    	try {
			initiateFiles();
		} catch (Exception exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
    	
    	//Action des boutons de score HOME
    	homeTD.addActionListener(e -> {
            previousScoreHome = scoreHome;
            if(!isTDHome){
            	scoreHome += 6;
            	homeTextScore.setText(Integer.toString(scoreHome));
            	try {updateHomeScore(scoreHome);} catch (Exception exc) {exc.printStackTrace();};
            	isTDHome = true; transformation();
            	}
            else { initiateAllButtons(); homeTD.setText("TD");}
        });
    	
    	homeTransfo1.addActionListener(e -> {
            previousScoreHome = scoreHome;
            scoreHome += 1;
            homeTextScore.setText(Integer.toString(scoreHome));
            try {updateHomeScore(scoreHome);} catch (Exception exc) {exc.printStackTrace();};;
            homeTD.setText("TD"); isTDHome = false; initiateAllButtons();
        });
    	
    	homeTransfo2.addActionListener(e -> {
            previousScoreHome = scoreHome;
            scoreHome += 2;
            homeTextScore.setText(Integer.toString(scoreHome));
            try {updateHomeScore(scoreHome);} catch (Exception exc) {exc.printStackTrace();};
            homeTD.setText("TD"); isTDHome = false; initiateAllButtons();
        });
    	
    	homeFG.addActionListener(e -> {
            previousScoreHome = scoreHome;
            scoreHome += 3;
            homeTextScore.setText(Integer.toString(scoreHome));
            try {updateHomeScore(scoreHome);} catch (Exception exc) {exc.printStackTrace();};
        });
    	
    	homeSafety.addActionListener(e -> {
            previousScoreHome = scoreHome;
            scoreHome += 2;
            homeTextScore.setText(Integer.toString(scoreHome));
            try {updateHomeScore(scoreHome);} catch (Exception exc) {exc.printStackTrace();};
            if(isTDAway) {initiateAllButtons(); awayTD.setText("TD");}
        });
        
    	
    	homeReset.addActionListener(e -> {
            scoreHome = previousScoreHome;
            previousScoreHome = 0;
            homeTextScore.setText(Integer.toString(scoreHome));
            try {updateHomeScore(scoreHome);} catch (Exception exc) {exc.printStackTrace();};
            if(isTDHome || isTDAway) {initiateAllButtons();}
        });
    	
    	//Action des boutons de score AWAY
    	awayTD.addActionListener(e -> {
    		previousScoreAway = scoreAway;
            if(!isTDAway){
            	scoreAway += 6;
            	awayTextScore.setText(Integer.toString(scoreAway));
                try {updateAwayScore(scoreAway);} catch (Exception exc) {exc.printStackTrace();};
                isTDAway = true; transformation();
            }
            else { initiateAllButtons(); awayTD.setText("TD");}            
        });
    	
    	awayTransfo1.addActionListener(e -> {
    		previousScoreAway = scoreAway;
            scoreAway += 1;
            awayTextScore.setText(Integer.toString(scoreAway));
            try {updateAwayScore(scoreAway);} catch (Exception exc) {exc.printStackTrace();};;
            awayTD.setText("TD"); isTDAway = false; initiateAllButtons();
        });
    	
    	awayTransfo2.addActionListener(e -> {
    		previousScoreAway = scoreAway;
            scoreAway += 2;
            awayTextScore.setText(Integer.toString(scoreAway));
            try {updateAwayScore(scoreAway);} catch (Exception exc) {exc.printStackTrace();};
            awayTD.setText("TD"); isTDAway = false; initiateAllButtons();
        });
    	
    	awayFG.addActionListener(e -> {
    		previousScoreAway = scoreAway;
            scoreAway += 3;
            awayTextScore.setText(Integer.toString(scoreAway));
            try {updateAwayScore(scoreAway);} catch (Exception exc) {exc.printStackTrace();};
        });
    	
    	awaySafety.addActionListener(e -> {
            previousScoreAway = scoreAway;
            scoreAway += 2;
            awayTextScore.setText(Integer.toString(scoreAway));
            try {updateAwayScore(scoreAway);} catch (Exception exc) {exc.printStackTrace();};
            if(isTDHome){initiateAllButtons(); homeTD.setText("TD");}
        });        
    	
    	awayReset.addActionListener(e -> {
    		scoreAway = previousScoreAway;
            previousScoreAway = 0;
            awayTextScore.setText(Integer.toString(scoreAway));
            try {updateAwayScore(scoreAway);} catch (Exception exc) {exc.printStackTrace();};
            if(isTDHome || isTDAway) {initiateAllButtons();}
        });
    	
    	//Action des boutons de temps
    	q1.addActionListener(e -> {
            actualQuarter = "Q1";
            timeText.setText("QUART TEMPS ACTUEL : " + actualQuarter);
            try {updateQuarter(actualQuarter);} catch (Exception exc) {exc.printStackTrace();};
        });
    	
    	q2.addActionListener(e -> {
            actualQuarter = "Q2";
            timeText.setText("QUART TEMPS ACTUEL : " + actualQuarter);
            try {updateQuarter(actualQuarter);} catch (Exception exc) {exc.printStackTrace();};
        });
    	
    	q3.addActionListener(e -> {
            actualQuarter = "Q3";
            timeText.setText("QUART TEMPS ACTUEL : " + actualQuarter);
            try {updateQuarter(actualQuarter);} catch (Exception exc) {exc.printStackTrace();};
        });
    	
    	q4.addActionListener(e -> {
            actualQuarter = "Q4";
            timeText.setText("QUART TEMPS ACTUEL : " + actualQuarter);
            try {updateQuarter(actualQuarter);} catch (Exception exc) {exc.printStackTrace();};
        });
    	
    }
    
    private void updateHomeScore(int homeScore) throws Exception{
    	
    	RandomAccessFile raf = new RandomAccessFile(fileScoreHome, "rws");
    	raf.setLength(0);
    	raf.writeBytes(Integer.toString(homeScore));
    	raf.close();
    	
    }
    
    private void updateAwayScore(int awayScore) throws Exception{
    	
    	RandomAccessFile raf = new RandomAccessFile(fileScoreAway, "rws");
    	raf.setLength(0);
    	raf.writeBytes(Integer.toString(awayScore));
    	raf.close();
    	
    }

    private void updateQuarter(String quarter) throws Exception{
	
    	RandomAccessFile raf = new RandomAccessFile(fileQuarter, "rws");
    	raf.setLength(0);
    	raf.writeBytes(quarter);
    	raf.close();
    	
    }
    
    private void transformation(){
    	if (isTDHome){
    		homeTD.setText("+0");
    		homeTransfo1.setEnabled(true);
    		homeTransfo2.setEnabled(true);
    		homeFG.setEnabled(false);
    		homeSafety.setEnabled(false);
    		
    		awayTD.setEnabled(false);
    		awayFG.setEnabled(false);
    		awayReset.setEnabled(false);
    	}
    	
    	if (isTDAway){
    		awayTD.setText("+0");
    		awayTransfo1.setEnabled(true);
    		awayTransfo2.setEnabled(true);
    		awayFG.setEnabled(false);
    		awaySafety.setEnabled(false);
    		
    		homeTD.setEnabled(false);
    		homeFG.setEnabled(false);
    		homeReset.setEnabled(false);
    	}
    }
    
    private void initiateWindow(){
    	/*
    	 *  Cette méthode instancie la fenêtre.
    	 */
    	
    	//Indicateur équipe domicile
        home = new JPanel();
        home.setLayout(null);

        //Label HOME
        homeTextArea = new JLabel("HOME");
        homeTextArea.setBounds(140, 0, 400, 75);
        homeTextArea.setFont((new Font(Font.SERIF, Font.BOLD, 40)));
        home.add(homeTextArea);
        
        //Score HOME
        homeTextScore = new JLabel("0");
        homeTextScore.setBounds(190, 75, 400, 75);
        homeTextScore.setFont((new Font(Font.SERIF, Font.BOLD, 40)));
        home.add(homeTextScore);

        //BOUTONS HOME
        homeTD=new JButton("TD");
        homeTD.setBounds(50,200,100,50);        
        home.add(homeTD);
        
        homeTransfo1=new JButton("+1");
        homeTransfo1.setBounds(150,200,100,50);
        home.add(homeTransfo1);
        
        homeTransfo2=new JButton("+2");
        homeTransfo2.setBounds(250,200,100,50);
        home.add(homeTransfo2);
        
        homeFG=new JButton("FG");
        homeFG.setBounds(50,250,100,50);
        home.add(homeFG);
        
        homeSafety=new JButton("SF");
        homeSafety.setBounds(150,250,100,50);
        home.add(homeSafety);
        
        homeReset=new JButton("RESET");
        homeReset.setBounds(250,250,100,50);
        home.add(homeReset);

        //File fetching HOME
        homeGetFileTextArea = new JLabel("Fichier utilisé : ");
        homeGetFileTextArea.setBounds(25, 300, 400, 75);
        homeGetFileTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 20)));
        home.add(homeGetFileTextArea);
        
        homeFilePath = new JTextField();
        homeFilePath.setBounds(25, 350, 350, 30);
        homeFilePath.setText("");
        home.add(homeFilePath);
        

        home.setBounds(25,0, 400, 400);
        home.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        home.setVisible(true);
        f.add(home);

        //Indicateur équipe extérieur
        away = new JPanel();
        away.setLayout(null);

        //Label AWAY
        awayTextArea = new JLabel("AWAY");
        awayTextArea.setBounds(140,0, 400,75);
        awayTextArea.setFont((new Font(Font.SERIF, Font.BOLD, 40)));
        away.add(awayTextArea);
        //Score AWAY
        awayTextScore = new JLabel("0");
        awayTextScore.setBounds(190,75, 400,75);
        awayTextScore.setFont((new Font(Font.SERIF, Font.BOLD, 40)));
        away.add(awayTextScore);

        //BOUTONS AWAY
        awayTD=new JButton("TD");
        awayTD.setEnabled(false);
        awayTD.setBounds(50,200,100,50);
        away.add(awayTD);
        
        awayTransfo1=new JButton("+1");
        awayTransfo1.setEnabled(false);
        awayTransfo1.setBounds(150,200,100,50);
        away.add(awayTransfo1);
        
        awayTransfo2=new JButton("+2");
        awayTransfo2.setEnabled(false);
        awayTransfo2.setBounds(250,200,100,50);
        away.add(awayTransfo2);
        
        awayFG=new JButton("FG");
        awayFG.setEnabled(false);
        awayFG.setBounds(50,250,100,50);
        away.add(awayFG);
        
        awaySafety=new JButton("SF");
        awaySafety.setEnabled(false);
        awaySafety.setBounds(150,250,100,50);
        away.add(awaySafety);
        
        awayReset=new JButton("RESET");
        awayReset.setEnabled(false);
        awayReset.setBounds(250,250,100,50);        
        away.add(awayReset);
        
        //File fetching AWAY
        awayGetFileTextArea = new JLabel("Fichier utilisé : ");
        awayGetFileTextArea.setBounds(25, 300, 400, 75);
        awayGetFileTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 20)));
        away.add(awayGetFileTextArea);
        
        awayFilePath = new JTextField();
        awayFilePath.setBounds(25, 350, 350, 30);
        awayFilePath.setText("");
        away.add(awayFilePath);
        
        away.setBounds(450,0, 400, 400);
        away.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        away.setVisible(true);
        f.add(away);
        
        //Label de temps :
        String time = "QUART TEMPS ACTUEL : " + actualQuarter;
        timeText = new JLabel(time);
        timeText.setBounds(275,385, 400,75);
        timeText.setFont((new Font(Font.SERIF, Font.BOLD, 25)));
        f.add(timeText);

        //Boutons de temps
        q1=new JButton("Q1");
        q1.setEnabled(false);
        q1.setBounds(200,500,100,50);
        f.add(q1);
        
        q2=new JButton("Q2");
        q2.setEnabled(false);
        q2.setBounds(325,500,100,50);
        f.add(q2);
        
        q3=new JButton("Q3");
        q3.setEnabled(false);
        q3.setBounds(450,500,100,50);
        f.add(q3);
        
        q4=new JButton("Q4");
        q4.setEnabled(false);
        q4.setBounds(575,500,100,50);
        f.add(q4);
        
        //File fetching temps
        quarterGetFileTextArea = new JLabel("Fichier utilisé : ");
        quarterGetFileTextArea.setBounds(190, 425, 400, 75);
        quarterGetFileTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 20)));
        f.add(quarterGetFileTextArea);
        
        quarterFilePath = new JTextField();
        quarterFilePath.setBounds(325, 450, 350, 30);
        quarterFilePath.setText("");
        f.add(quarterFilePath);

        f.setSize(900,600);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    }
    
    private void initiateFiles() throws Exception{
    	
    	fileScoreHome = new File(System.getProperty("user.dir") + "/files/ScoreHome.txt");
    	fileScoreHome.createNewFile();
    	updateHomeScore(0);
    	homeFilePath.setText(fileScoreHome.getAbsolutePath());
    	homeFilePath.setEnabled(false);
    	
    	fileScoreAway = new File(System.getProperty("user.dir") + "/files/ScoreAway.txt");
    	fileScoreAway.createNewFile();
    	updateAwayScore(0);
    	awayFilePath.setText(fileScoreAway.getAbsolutePath());
    	awayFilePath.setEnabled(false);
    	
    	fileQuarter = new File(System.getProperty("user.dir") + "/files/Quarter.txt");
    	fileQuarter.createNewFile();
    	updateQuarter("Q1");
    	quarterFilePath.setText(fileQuarter.getAbsolutePath());
    	quarterFilePath.setEnabled(false);
    	
    	initiateAllButtons();
    	
    }
    
    private void initiateAllButtons(){
    	
    	isTDHome = false;
    	isTDAway = false;
    	
    	homeTD.setEnabled(true);
    	homeTransfo1.setEnabled(false);
    	homeTransfo2.setEnabled(false);
    	homeFG.setEnabled(true);
    	homeSafety.setEnabled(true);
    	homeReset.setEnabled(true);
    	awayTD.setEnabled(true);
    	awayTransfo1.setEnabled(false);
    	awayTransfo2.setEnabled(false);
    	awayFG.setEnabled(true);
    	awaySafety.setEnabled(true);
    	awayReset.setEnabled(true);
    	q1.setEnabled(true);
    	q2.setEnabled(true);
    	q3.setEnabled(true);
    	q4.setEnabled(true);
    	
    }
    
}