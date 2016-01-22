import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;


public class SelectFiles extends JPanel
   implements ActionListener {
   JButton go;
   

   JFileChooser chooser;
   String choosertitle;
   private String fileChoosed;
   
  public SelectFiles() {
    go = new JButton("Choose your file");
    go.addActionListener(this);
    add(go);
   }

    public String getFilePath(){
        if(fileChoosed == null)
            return null;
        return fileChoosed;

    }
    public void actionPerformed(ActionEvent e) {
        int result;
            
        chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("./"));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
             /*chooser.getCurrentDirectory() + "@@");
            System.out.println("getSelectedFile() : " 
                +  chooser.getSelectedFile()+"@@");*/
            File file = chooser.getSelectedFile();
            System.out.println("Selected file is: "+file.getAbsolutePath()+"/"+file.getName());
            fileChoosed = file.getAbsolutePath()+"/"+file.getName();
        }else{
                fileChoosed = null;
                 System.out.println("Didn't choose file\n");

        }
    }
   
  public Dimension getPreferredSize(){
    return new Dimension(200, 200);
    }
    
  public static void main(String s[]) {
    JFrame frame = new JFrame("");
    SelectFiles panel = new SelectFiles();
    frame.addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
          }
        }
      );
    frame.getContentPane().add(panel,"Center");
    frame.setSize(panel.getPreferredSize());
    frame.setVisible(true);
    }
}