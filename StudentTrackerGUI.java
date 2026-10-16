import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public class StudentTrackerGUI {
    static final String FILE_NAME = "students.txt";
    static final Color NAVY = new Color(22, 45, 85);
    static final Color BLUE = new Color(37, 99, 235);
    static final Color BG = new Color(244, 247, 252);
    static final Color GREEN = new Color(22, 163, 110);
    static final Font BODY = new Font("Segoe UI", Font.PLAIN, 14);
    static JFrame dashboard;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            showLogin();
        });
    }

    static void showLogin() {
    JFrame frame = new JFrame("Student Placement Tracker - Login");
    frame.setSize(450, 420);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);

    Color blue = new Color(30, 90, 180);
    Color background = new Color(240, 245, 255);

    JPanel main = new JPanel(new BorderLayout(15, 15));
    main.setBackground(background);
    main.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

    JPanel header = new JPanel(new GridLayout(2, 1, 5, 5));
    header.setBackground(blue);
    header.setBorder(BorderFactory.createEmptyBorder(18, 10, 18, 10));

    JLabel title = new JLabel("STUDENT TRACKER", JLabel.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 23));
    title.setForeground(Color.WHITE);

    JLabel subtitle = new JLabel("Admin Login", JLabel.CENTER);
    subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
    subtitle.setForeground(Color.WHITE);

    header.add(title);
    header.add(subtitle);

    JPanel form = new JPanel(new GridLayout(4, 1, 8, 8));
    form.setBackground(background);

    JLabel userLabel = new JLabel("Username");
    userLabel.setFont(new Font("Arial", Font.BOLD, 14));

    JTextField username = new JTextField();
    username.setFont(new Font("Arial", Font.PLAIN, 15));

    JLabel passLabel = new JLabel("Password");
    passLabel.setFont(new Font("Arial", Font.BOLD, 14));

    JPasswordField password = new JPasswordField();
    password.setFont(new Font("Arial", Font.PLAIN, 15));

    form.add(userLabel);
    form.add(username);
    form.add(passLabel);
    form.add(password);

    JPanel bottom = new JPanel(new GridLayout(2, 1, 8, 8));
    bottom.setBackground(background);

    JButton login = new JButton("LOGIN");
    login.setFont(new Font("Arial", Font.BOLD, 16));
    login.setForeground(Color.WHITE);
    login.setBackground(blue);
    login.setOpaque(true);
    login.setBorderPainted(false);
    login.setFocusPainted(false);
    login.setPreferredSize(new Dimension(200, 45));
    login.setCursor(new Cursor(Cursor.HAND_CURSOR));

    JLabel footer = new JLabel(
        "Student Placement Management System", JLabel.CENTER
    );
    footer.setFont(new Font("Arial", Font.ITALIC, 11));
    footer.setForeground(Color.DARK_GRAY);

    bottom.add(login);
    bottom.add(footer);

    main.add(header, BorderLayout.NORTH);
    main.add(form, BorderLayout.CENTER);
    main.add(bottom, BorderLayout.SOUTH);

    frame.setContentPane(main);

    login.addActionListener(e -> {
        String user = username.getText().trim();
        String pass = new String(password.getPassword());

        if (user.equals("admin") && pass.equals("1234")) {
            frame.dispose();
            showDashboard();
        } else {
            JOptionPane.showMessageDialog(
                frame,
                "Invalid username or password!",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE
            );
            password.setText("");
            password.requestFocusInWindow();
        }
    });

    password.addActionListener(e -> login.doClick());

    frame.getRootPane().setDefaultButton(login);
    frame.setVisible(true);
    username.requestFocusInWindow();
}

    static void showDashboard() {
        dashboard = new JFrame("Student Placement Tracker"); dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); dashboard.setMinimumSize(new Dimension(800,650)); dashboard.setSize(900,720); dashboard.setLocationRelativeTo(null);
        JPanel root = new JPanel(new BorderLayout(0,0)); root.setBackground(BG);
        JPanel header = new JPanel(new BorderLayout()); header.setBackground(NAVY); header.setBorder(new EmptyBorder(22,30,22,30));
        JPanel titles = new JPanel(new GridLayout(0,1,4,4)); titles.setOpaque(false);
        JLabel title = new JLabel("Student Placement Tracker"); title.setForeground(Color.WHITE); title.setFont(new Font("Segoe UI",Font.BOLD,27));
        JLabel subtitle = new JLabel("Manage student progress and placement readiness"); subtitle.setForeground(new Color(213,224,245)); subtitle.setFont(BODY); titles.add(title); titles.add(subtitle);
        JButton logout = button("Logout", new Color(65,85,125)); logout.addActionListener(e -> { dashboard.dispose(); showLogin(); }); header.add(titles,BorderLayout.CENTER); header.add(logout,BorderLayout.EAST);
        JPanel content = new JPanel(new BorderLayout(0,18)); content.setBackground(BG); content.setBorder(new EmptyBorder(22,26,22,26));
        JPanel stats = new JPanel(new GridLayout(1,4,14,14)); stats.setBackground(BG); int[] s = getStats();
        stats.add(card("TOTAL STUDENTS",s[0],BLUE)); stats.add(card("PLACEMENT ELIGIBLE",s[1],GREEN)); stats.add(card("TOPICS COMPLETED",s[2],new Color(124,83,196))); stats.add(card("PLACEMENT RATE",s[3]+"%",new Color(217,119,6)));
        JPanel section = new JPanel(new BorderLayout(0,12)); section.setBackground(BG);
        JLabel heading = new JLabel("Student Management"); heading.setFont(new Font("Segoe UI",Font.BOLD,20)); heading.setForeground(NAVY); section.add(heading,BorderLayout.NORTH);
        JPanel grid = new JPanel(new GridLayout(3,2,14,14)); grid.setBackground(BG);
        addActionCard(grid,"＋  Add Student","Create a new student record",BLUE,StudentTrackerGUI::addStudent);
        addActionCard(grid,"⌕  Search Student","Find a student by name",new Color(13,148,136),StudentTrackerGUI::searchStudent);
        addActionCard(grid,"▤  View All Students","Browse records in a table",new Color(79,70,229),StudentTrackerGUI::viewAllStudents);
        addActionCard(grid,"▥  Placement Report","View eligibility summary",new Color(217,119,6),StudentTrackerGUI::placementReport);
        addActionCard(grid,"✎  Update Student","Edit an existing record",new Color(8,145,178),StudentTrackerGUI::updateStudent);
        addActionCard(grid,"－  Delete Student","Remove a record safely",new Color(220,70,70),StudentTrackerGUI::deleteStudent);
        section.add(grid,BorderLayout.CENTER);
        JLabel foot = new JLabel("  Student Placement Management System  •  Data saved locally",JLabel.CENTER); foot.setFont(new Font("Segoe UI",Font.PLAIN,12)); foot.setForeground(new Color(100,116,139)); foot.setBorder(new EmptyBorder(0,0,12,0));
        content.add(stats,BorderLayout.NORTH); content.add(section,BorderLayout.CENTER); root.add(header,BorderLayout.NORTH); root.add(content,BorderLayout.CENTER); root.add(foot,BorderLayout.SOUTH); dashboard.setContentPane(root); dashboard.setVisible(true);
    }

    static JPanel card(String heading, Object value, Color accent) {
        JPanel p=new JPanel(new BorderLayout(0,10)); p.setBackground(Color.WHITE); p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,4,0,0,accent),BorderFactory.createEmptyBorder(17,16,17,12)));
        JLabel h=new JLabel(heading); h.setFont(new Font("Segoe UI",Font.BOLD,11)); h.setForeground(new Color(100,116,139)); JLabel v=new JLabel(String.valueOf(value)); v.setFont(new Font("Segoe UI",Font.BOLD,27)); v.setForeground(accent); p.add(h,BorderLayout.NORTH); p.add(v,BorderLayout.CENTER); return p;
    }
    interface Action { void run(); }
    static void addActionCard(JPanel parent,String title,String desc,Color accent,Action action){
        JButton b=new JButton("<html><div style='text-align:left;padding:5px'><span style='font-size:15pt'><b>"+title+"</b></span><br><span style='color:#526078;font-size:10pt'>"+desc+"</span></div></html>"); b.setHorizontalAlignment(SwingConstants.LEFT); b.setBackground(Color.WHITE); b.setForeground(accent); b.setFont(BODY); b.setFocusPainted(false); b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(225,231,240)),BorderFactory.createMatteBorder(0,4,0,0,accent))); b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); b.addActionListener(e->action.run()); parent.add(b);
    }
    static JLabel label(String text){ JLabel l=new JLabel(text); l.setFont(new Font("Segoe UI",Font.BOLD,13)); l.setForeground(NAVY); return l; }
    static void styleField(JTextField f){ f.setFont(BODY); f.setPreferredSize(new Dimension(250,36)); f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200,210,225)),new EmptyBorder(5,9,5,9))); }
    static JButton button(String text, Color color) {
    JButton b = new JButton(text);
    b.setFont(new Font("Segoe UI", Font.BOLD, 13));
    b.setForeground(Color.WHITE);
    b.setBackground(color);
    b.setOpaque(true);
    b.setContentAreaFilled(true);
    b.setBorderPainted(false);
    b.setFocusPainted(false);
    b.setBorder(new EmptyBorder(10, 18, 10, 18));
    b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    return b;
}
    static int[] getStats(){ int total=0,eligible=0,completed=0; try{ List<List<String>> rs=readRecords(); total=rs.size(); for(List<String> r:rs){ if(val(r,"Placement Status").equalsIgnoreCase("Eligible")) eligible++; if(val(r,"Status").equalsIgnoreCase("Completed")) completed++; }}catch(IOException e){} int rate=total==0?0:(int)Math.round(eligible*100.0/total); return new int[]{total,eligible,completed,rate}; }
    static void refreshDashboard(){ if(dashboard!=null && dashboard.isDisplayable()){ dashboard.dispose(); showDashboard(); } }

    static void addStudent(){
        JTextField name=new JTextField(),skill=new JTextField(),total=new JTextField(),done=new JTextField(); JCheckBox aptitude=new JCheckBox("Aptitude completed"),coding=new JCheckBox("Coding completed");
        Object[] fields={"Student Name:",name,"Skill / Technology:",skill,"Total Topics:",total,"Completed Topics:",done,aptitude,coding};
        if(JOptionPane.showConfirmDialog(dashboard,fields,"Add Student",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE)!=JOptionPane.OK_OPTION)return;
        try{ String n=name.getText().trim(),sk=skill.getText().trim(); int t=Integer.parseInt(total.getText().trim()),d=Integer.parseInt(done.getText().trim()); if(n.isEmpty()||sk.isEmpty()||t<=0||d<0||d>t){error("Enter a name, skill, total topics above 0, and completed topics between 0 and total.");return;} List<List<String>> rs=readRecords(); for(List<String> r:rs)if(val(r,"Name").equalsIgnoreCase(n)){error("A student with this name already exists. Use Update Student to edit the record.");return;} rs.add(makeRecord(n,sk,t,d,aptitude.isSelected(),coding.isSelected())); writeRecords(rs); success("Student added successfully."); refreshDashboard(); }catch(NumberFormatException e){error("Total Topics and Completed Topics must be whole numbers.");}catch(IOException e){error("Could not save the student record: "+e.getMessage());}
    }
    static void searchStudent(){ String n=JOptionPane.showInputDialog(dashboard,"Enter student name:","Search Student",JOptionPane.QUESTION_MESSAGE); if(n==null||n.trim().isEmpty())return; try{for(List<String> r:readRecords())if(val(r,"Name").equalsIgnoreCase(n.trim())){showRecord(r,"Student Found");return;} info("Student not found.");}catch(IOException e){error("Could not read student data.");} }
    static void viewAllStudents(){ try{List<List<String>> rs=readRecords(); if(rs.isEmpty()){info("No student records found. Add a student to get started.");return;} String[] cols={"Name","Skill","Total Topics","Completed Topics","Remaining","Progress","Status","Aptitude","Coding","Placement"}; DefaultTableModel model=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}}; for(List<String> r:rs)model.addRow(new Object[]{val(r,"Name"),val(r,"Skill"),val(r,"Total Topics"),val(r,"Completed Topics"),val(r,"Remaining Topics"),val(r,"Progress"),val(r,"Status"),val(r,"Aptitude Completed"),val(r,"Coding Completed"),val(r,"Placement Status")}); JTable table=new JTable(model); table.setFont(BODY); table.setRowHeight(27); table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,12)); table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); JScrollPane pane=new JScrollPane(table); pane.setPreferredSize(new Dimension(1000,330)); JOptionPane.showMessageDialog(dashboard,pane,"All Student Records",JOptionPane.PLAIN_MESSAGE);}catch(IOException e){error("Could not read student data.");} }
    static void placementReport(){ int[] s=getStats(); int not=s[0]-s[1]; String report="PLACEMENT REPORT\n\nTotal Students: "+s[0]+"\nEligible Students: "+s[1]+"\nNot Eligible Students: "+not+"\nTopics Completed Fully: "+s[2]+"\nPlacement Eligibility Rate: "+s[3]+"%"; JOptionPane.showMessageDialog(dashboard,report,"Placement Report",JOptionPane.INFORMATION_MESSAGE); }
    static void updateStudent(){ String n=JOptionPane.showInputDialog(dashboard,"Enter the current student name:","Update Student",JOptionPane.QUESTION_MESSAGE); if(n==null||n.trim().isEmpty())return; try{List<List<String>> rs=readRecords(); for(int i=0;i<rs.size();i++){List<String> r=rs.get(i); if(val(r,"Name").equalsIgnoreCase(n.trim())){ JTextField name=new JTextField(val(r,"Name")),skill=new JTextField(val(r,"Skill")),total=new JTextField(val(r,"Total Topics")),done=new JTextField(val(r,"Completed Topics")); JCheckBox a=new JCheckBox("Aptitude completed",Boolean.parseBoolean(val(r,"Aptitude Completed"))),c=new JCheckBox("Coding completed",Boolean.parseBoolean(val(r,"Coding Completed"))); Object[] f={"Student Name:",name,"Skill / Technology:",skill,"Total Topics:",total,"Completed Topics:",done,a,c}; if(JOptionPane.showConfirmDialog(dashboard,f,"Update Student",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE)!=JOptionPane.OK_OPTION)return; String nn=name.getText().trim(),sk=skill.getText().trim(); int t=Integer.parseInt(total.getText().trim()),d=Integer.parseInt(done.getText().trim()); if(nn.isEmpty()||sk.isEmpty()||t<=0||d<0||d>t){error("Please enter valid student details and topic numbers.");return;} for(int j=0;j<rs.size();j++)if(j!=i&&val(rs.get(j),"Name").equalsIgnoreCase(nn)){error("Another student already uses this name.");return;} rs.set(i,makeRecord(nn,sk,t,d,a.isSelected(),c.isSelected())); writeRecords(rs); success("Student updated successfully.");refreshDashboard();return;}} info("Student not found.");}catch(NumberFormatException e){error("Topic values must be whole numbers.");}catch(IOException e){error("Could not update student data.");} }
    static void deleteStudent(){ String n=JOptionPane.showInputDialog(dashboard,"Enter student name to delete:","Delete Student",JOptionPane.QUESTION_MESSAGE); if(n==null||n.trim().isEmpty())return; try{List<List<String>> rs=readRecords(); for(int i=0;i<rs.size();i++)if(val(rs.get(i),"Name").equalsIgnoreCase(n.trim())){int c=JOptionPane.showConfirmDialog(dashboard,"Permanently delete "+val(rs.get(i),"Name")+"?","Confirm Delete",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE); if(c!=JOptionPane.YES_OPTION)return; rs.remove(i);writeRecords(rs);success("Student deleted successfully.");refreshDashboard();return;} info("Student not found.");}catch(IOException e){error("Could not delete student data.");} }
    static List<String> makeRecord(String n,String sk,int t,int d,boolean a,boolean c){ int rem=t-d; double p=d*100.0/t; String status=d==0?"Not Started":d==t?"Completed":"In Progress"; String placement=a&&c?"Eligible":"Not Eligible"; return new ArrayList<>(Arrays.asList("Name: "+n,"Skill: "+sk,"Total Topics: "+t,"Completed Topics: "+d,"Remaining Topics: "+rem,String.format(Locale.US,"Progress: %.2f%%",p),"Status: "+status,"Aptitude Completed: "+a,"Coding Completed: "+c,"Placement Status: "+placement)); }
    static void showRecord(List<String> r,String title){ StringBuilder b=new StringBuilder(); for(String line:r)b.append(line).append('\n'); JOptionPane.showMessageDialog(dashboard,b.toString(),title,JOptionPane.INFORMATION_MESSAGE); }
    static String val(List<String> r,String key){ for(String line:r){int i=line.indexOf(':'); if(i>0&&line.substring(0,i).trim().equalsIgnoreCase(key))return line.substring(i+1).trim();} return ""; }
    static List<List<String>> readRecords() throws IOException { List<List<String>> records=new ArrayList<>(); Path p=Paths.get(FILE_NAME); if(!Files.exists(p))return records; List<String> lines=Files.readAllLines(p); List<String> cur=new ArrayList<>(); for(String line:lines){if(line.trim().startsWith("----------------")){if(!cur.isEmpty()){records.add(new ArrayList<>(cur));cur.clear();}}else if(!line.trim().isEmpty())cur.add(line);} if(!cur.isEmpty())records.add(cur); return records; }
    static void writeRecords(List<List<String>> records) throws IOException { Path target=Paths.get(FILE_NAME),temp=Paths.get(FILE_NAME+".tmp"); try(BufferedWriter w=Files.newBufferedWriter(temp)){for(List<String> r:records){for(String line:r){w.write(line);w.newLine();}w.write("------------------------------");w.newLine();}} try{Files.move(temp,target,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);}catch(AtomicMoveNotSupportedException e){Files.move(temp,target,StandardCopyOption.REPLACE_EXISTING);} }
    static void success(String s){JOptionPane.showMessageDialog(dashboard,s,"Success",JOptionPane.INFORMATION_MESSAGE);} static void info(String s){JOptionPane.showMessageDialog(dashboard,s,"Student Tracker",JOptionPane.INFORMATION_MESSAGE);} static void error(String s){JOptionPane.showMessageDialog(dashboard,s,"Please Check",JOptionPane.ERROR_MESSAGE);}
}