import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private JLabel currentGoldLabel;
    private JLabel currentLivesLabel;
    private JLabel currentKillsLabel;
    private JLabel currentWaveLabel;
    public InfoPanel() {


        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(300, 400));
        this.setMinimumSize(new Dimension(300, 400));
        this.setSize(300, 400);
        this.setLayout(new GridLayout(4, 2));

        currentGoldLabel = new JLabel("25"); //TODO Update Gold
        currentLivesLabel = new JLabel("3"); //TODO Update Lives when necessary
        currentKillsLabel = new JLabel("0"); //TODO Update Kills
        currentWaveLabel = new JLabel("0"); //TODO Update Wave

        JLabel temp = new JLabel("Gold:");
        temp.setHorizontalAlignment(SwingConstants.RIGHT);
        temp.setFont(new Font("Serif", Font.BOLD, 20));
        temp.setForeground(Color.ORANGE);
        this.add(temp);this.add(currentGoldLabel);
        temp = new JLabel("Lives:");
        temp.setHorizontalAlignment(SwingConstants.RIGHT);
        temp.setFont(new Font("Serif", Font.BOLD, 20));
        temp.setForeground(Color.GREEN);
        this.add(temp);this.add(currentLivesLabel);
        temp = new JLabel("Kills:");
        temp.setHorizontalAlignment(SwingConstants.RIGHT);
        temp.setFont(new Font("Serif", Font.BOLD, 20));
        temp.setForeground(Color.RED);
        this.add(temp);this.add(currentKillsLabel);
        temp = new JLabel("Wave:");
        temp.setHorizontalAlignment(SwingConstants.RIGHT);
        temp.setFont(new Font("Serif", Font.BOLD, 20));
        this.add(temp);this.add(currentWaveLabel);


        currentGoldLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentLivesLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentKillsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentWaveLabel.setHorizontalAlignment(SwingConstants.LEFT);

        currentGoldLabel.setFont(new Font("Serif", Font.BOLD, 20));
        currentLivesLabel.setFont(new Font("Serif", Font.BOLD, 20));
        currentKillsLabel.setFont(new Font("Serif", Font.BOLD, 20));
        currentWaveLabel.setFont(new Font("Serif", Font.BOLD, 20));
    }


    //TODO
    public void updateGold(int Gold) {
        currentGoldLabel.setText(Integer.toString(Gold));
    }

    public void updateLive(int Live) {
        this.currentLivesLabel.setText(Integer.toString(Live));
    }

    public void updateKills(int Kills) {
        this.currentKillsLabel.setText(Integer.toString(Kills));
    }

    public void updateWave(int Wave) {
        this.currentWaveLabel.setText(Integer.toString(Wave));
    }

}
