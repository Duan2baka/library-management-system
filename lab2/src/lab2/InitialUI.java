package lab2;


import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.Iterator;
import java.io.*;

public class InitialUI {
   
    //////====================================Variables for MainUI=======================================
    
    JFrame frame = new JFrame();
    Date date = new Date();
    
    JTextArea message = new JTextArea("Student Name and ID: DUAN Mingfei (20099033D)\n" + "Student Name and ID: JI Hang (20077216D)\n" + date.toString());
    
    String header[] = {"ISBN", "Title", "Available"};
    DefaultTableModel model = new DefaultTableModel(null, header) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    JTable table = new JTable(model);
    JScrollPane scollPane = new JScrollPane(table);
    
    JTextField ISBN = new JTextField(20);
    JTextField Title = new JTextField(20);
    
    JButton jbt_Add = new JButton("Add");
    JButton jbt_Edit = new JButton("Edit");
    JButton jbt_Save = new JButton("Save");
    JButton jbt_Delete = new JButton("Delete");
    JButton jbt_Search = new JButton("Search");
    JButton jbt_More = new JButton("More>>");
    JButton jbt_Export = new JButton("Export");
    JButton jbt_Import = new JButton("Import");
    
    JButton jbt_Load = new JButton("Load Test Data");
    JButton jbt_Display_All = new JButton("Display All");
    JButton jbt_Display_by_ISBN = new JButton("Display All by ISBN");
    JButton jbt_Display_by_Title = new JButton("Display All by Title");
    JButton jbt_Exit = new JButton("Exit");

    //////====================================Variables for Dialog UI=======================================
    
    JButton jbt_Borrow = new JButton("Borrow");
    JButton jbt_Return = new JButton("Return");
    JButton jbt_Reserve = new JButton("Reserve");
    JButton jbt_Waiting = new JButton("Waiting Queue");

    JDialog tmpDialog = null;
    JTextArea info = null;
    JPanel tmpPanel = null;
    JPanel tmpPanel1 = null;
    JPanel iconPanel = null;
    JTextArea Msg = null;
    JLabel Jicon = null;
    ImageIcon icon = null;

    //////====================================Other Variables=======================================
    
    MyLinkedList<Book> book_list = new MyLinkedList<>();
    int sort_by_title = 0;
    int sort_by_isbn = 0;
    int selectedIndex = -1;
    Book selectedBook = null;
    
    
    
    public InitialUI(){
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(3,1));
        message.setEditable(false);
        
        main.add(message);
        main.add(scollPane);
        
        JPanel p1 = new JPanel();
        p1.add(new JLabel("ISBN:"));
        p1.add(ISBN);
        p1.add(new JLabel("Title:"));
        p1.add(Title);

        JPanel p2 = new JPanel();
        p2.add(jbt_Add);
        p2.add(jbt_Edit);
        p2.add(jbt_Save);
        p2.add(jbt_Delete);
        p2.add(jbt_Search);
        p2.add(jbt_More);
        p2.add(jbt_Import);
        p2.add(jbt_Export);

        JPanel p3 = new JPanel();
        p3.add(jbt_Load);
        p3.add(jbt_Display_All);
        p3.add(jbt_Display_by_ISBN);
        p3.add(jbt_Display_by_Title);
        p3.add(jbt_Exit);

        JPanel operations = new JPanel();
        operations.setLayout(new GridLayout(3, 1));
        operations.add(p1);
        operations.add(p2);
        operations.add(p3);

        main.add(operations);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                ISBN.setText(table.getValueAt(row, 0).toString());
                Title.setText(table.getValueAt(row, 1).toString());
            }
            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.getSelectedRow();
                ISBN.setText(table.getValueAt(row, 0).toString());
                Title.setText(table.getValueAt(row, 1).toString());
            }
        });
        
        jbt_Save.setEnabled(false);
        
        jbt_Add.addActionListener(new ButtonListener());
        jbt_Edit.addActionListener(new ButtonListener());
        jbt_Save.addActionListener(new ButtonListener());
        jbt_Delete.addActionListener(new ButtonListener());
        jbt_Search.addActionListener(new ButtonListener());
        jbt_More.addActionListener(new ButtonListener());
        jbt_Import.addActionListener(new ButtonListener());
        jbt_Export.addActionListener(new ButtonListener());
        jbt_Exit.addActionListener(new ButtonListener());
        
        jbt_Load.addActionListener(new ButtonListener());
        jbt_Display_All.addActionListener(new ButtonListener());
        jbt_Display_by_ISBN.addActionListener(new ButtonListener());
        jbt_Display_by_Title.addActionListener(new ButtonListener());

        jbt_Borrow.addActionListener(new ButtonListener());
        jbt_Return.addActionListener(new ButtonListener());
        jbt_Reserve.addActionListener(new ButtonListener());
        jbt_Waiting.addActionListener(new ButtonListener());
        
        frame.add(main);
        frame.setTitle("Library Admin System");
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jbt_Load) {
                addBook(new Book("HTML How to Program", "0131450913"));
                addBook(new Book("C++ How to Program",  "0131857576"));
                addBook(new Book("Java How to Program", "0132222205"));
            }
            else if (e.getSource() == jbt_Add) {
                if(Title.getText().length() == 0 || ISBN.getText().length() == 0) 
                    msg("ISBN and Title field should not be empty!");
                else addBook(new Book(Title.getText(), ISBN.getText()));
            }
            else if (e.getSource() == jbt_Edit) {
                selectedIndex = get_by_ISBN(ISBN.getText());
                if(selectedIndex == -1) msg("No record with ISBN " + ISBN.getText());
                else editMode(true);
            }
            else if (e.getSource() == jbt_Save) {
                if(get_by_ISBN(ISBN.getText()) != -1 && get_by_ISBN(ISBN.getText()) != selectedIndex)
                    msg("Error: book ISBN exists in the current database.");
                else {
                    Book selectedBook = book_list.get(selectedIndex);
                    selectedBook.setISBN(ISBN.getText());
                    selectedBook.setTitle(Title.getText());
                    ISBN.setText(null);
                    Title.setText(null);
                    editMode(false);
                    updateTable();
                }
            }
            else if (e.getSource() == jbt_Delete) {
                int index = get_by_ISBN(ISBN.getText());
                if(index == -1) msg("No record with ISBN " + ISBN.getText());
                else {
                    book_list.remove(index);
                    updateTable();
                }
            }
            else if (e.getSource() == jbt_Search) {
                String ISBN_ = ISBN.getText();
                String Title_ = Title.getText();
                if(ISBN_.length() == 0 && Title_.length() == 0) updateTable();
                else {
                    ArrayList<Book> arrl = new ArrayList<>();
                    Iterator<Book> it= book_list.iterator();
                    while(it.hasNext()) {
                        Book tmp = it.next();
                        if((ISBN_.length() != 0 && tmp.getISBN().toLowerCase().contains(ISBN_.toLowerCase())) || 
                        (Title_.length() != 0 && tmp.getTitle().toLowerCase().contains(Title_.toLowerCase())))
                            arrl.add(tmp);
                    }
                    Book [] arr = new Book[arrl.size()];
                    arrl.toArray(arr);
                    sort_by_title = sort_by_isbn = 0;
                    display(arr);
                }
            }
            else if (e.getSource() == jbt_More) {
                int index = get_by_ISBN(ISBN.getText());
                if(index == -1) msg("Error: book with ISBN " + ISBN.getText() + " doesn't exists in the current database.");
                else {
                    selectedBook = book_list.get(index);
                    tmpDialog = new JDialog(frame, selectedBook.getTitle());
                    info = new JTextArea("ISBN: " + selectedBook.getISBN() + "\nTitle: " + selectedBook.getTitle() + "\nAvailable: " + (selectedBook.isAvailable() ? "true" : "false"));
                    info.setEditable(false);
                    
                    if(selectedBook.getTitle().contains("C++")) icon = new ImageIcon("res/c++.jpg");
                    else if(selectedBook.getTitle().contains("Java")) icon = new ImageIcon("res/java.jpg");
                    else if(selectedBook.getTitle().contains("HTML")) icon = new ImageIcon("res/html.jpg");
                    else icon = new ImageIcon("res/default.jpg");
                    icon = new ImageIcon(icon.getImage().getScaledInstance(128, 167, java.awt.Image.SCALE_SMOOTH));
                    iconPanel = new JPanel();
                    Jicon = new JLabel(icon);
                    iconPanel.add(Jicon);
                    
                    tmpPanel = new JPanel();
                    tmpPanel.add(jbt_Borrow);
                    tmpPanel.add(jbt_Return);
                    tmpPanel.add(jbt_Reserve);
                    tmpPanel.add(jbt_Waiting);
                    
                    tmpPanel1 = new JPanel();

                    tmpPanel1.add(tmpPanel, BorderLayout.NORTH);
                    tmpPanel1.add(iconPanel, BorderLayout.CENTER);

                    Msg = new JTextArea();
                    
                    if(selectedBook.isAvailable()) {
                        jbt_Borrow.setEnabled(true);
                        jbt_Return.setEnabled(false);
                        jbt_Reserve.setEnabled(false);
                        jbt_Waiting.setEnabled(false);
                    }
                    else {
                        jbt_Borrow.setEnabled(false);
                        jbt_Return.setEnabled(true);
                        jbt_Reserve.setEnabled(true);
                        jbt_Waiting.setEnabled(true);
                    }

                    tmpDialog.getContentPane().add(info, BorderLayout.NORTH);
                    tmpDialog.getContentPane().add(tmpPanel1, BorderLayout.CENTER);
                    tmpDialog.getContentPane().add(Msg, BorderLayout.SOUTH);
                    tmpDialog.setSize(400, 400);
                    tmpDialog.setVisible(true);
                }
            }
            else if (e.getSource() == jbt_Export) {
            	try {
					File myObj = new File("output.txt");
					myObj.createNewFile();
        	    } catch (IOException ee) {
					JOptionPane.showMessageDialog(frame, "An error occurred.");
        	        ee.printStackTrace();
        	    }
            	try {
					FileWriter myWriter = new FileWriter("output.txt");
					Iterator<Book> it = book_list.iterator();
					while(it.hasNext()) {
						Book tmp = it.next();
						myWriter.write(tmp.getISBN() + "\n");
						myWriter.write(tmp.getTitle() + "\n");
						myWriter.write((tmp.isAvailable()? "true" : "false") + "\n");
						MyLinkedList<String> queue = tmp.getReservedQueue().getList();
						Iterator<String> it1 = queue.iterator();
						while(it1.hasNext()) 
							myWriter.write(it1.next() + "\n");
						myWriter.write("end_mark\n");
					}
					myWriter.write("final_mark\n");
					myWriter.close();
					JOptionPane.showMessageDialog(frame, "Export to file successfully!");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame, "An error occurred.");
					e1.printStackTrace();
				}
            }
            else if (e.getSource() == jbt_Import) {
            	File file = new File("output.txt");
            	BufferedReader reader = null;
            	if(!file.exists()) {
					JOptionPane.showMessageDialog(frame, "No input file!");
            		return;
            	}
            	try {
					reader = new BufferedReader(new FileReader(file));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame, "An error occurred.");
					e1.printStackTrace();
				}
            	String tempString = null;
            	int num = 0;
            	try {
            		MyLinkedList<Book> tmplist = book_list;
            		book_list = new MyLinkedList<>();
            		boolean errorflag = false;
					while ((tempString = reader.readLine()) != null) {
						num ++;
						if(tempString.contains("final_mark")) break;
						Book tmp = new Book();
						tmp.setISBN(tempString);
						tempString = reader.readLine();
						if(tempString == null) {
		            		book_list = tmplist;
							JOptionPane.showMessageDialog(frame, "An error occurred.");
							errorflag = true;
							break;
						}
						tmp.setTitle(tempString);
						tempString = reader.readLine();
						if(tempString == null) {
		            		book_list = tmplist;
							JOptionPane.showMessageDialog(frame, "An error occurred.");
							errorflag = true;
							break;
						}
						tmp.setAvailable(tempString.contains("true"));
						tempString = reader.readLine();
						if(tempString == null) {
		            		book_list = tmplist;
							JOptionPane.showMessageDialog(frame, "An error occurred.");
							errorflag = true;
							break;
						}
						while(!tempString.contains("end_mark")) {
							tmp.getReservedQueue().enqueue(tempString);
							tempString = reader.readLine();
							if(tempString == null) {
			            		book_list = tmplist;
								JOptionPane.showMessageDialog(frame, "An error occurred.");
								errorflag = true;
								break;
							}
						}
						if(errorflag) break;
						book_list.addLast(tmp);
						if(num > 10000) {
		            		book_list = tmplist;
							JOptionPane.showMessageDialog(frame, "An error occurred.");
							errorflag = true;
							break;
						}
					}
					if(!errorflag)
						JOptionPane.showMessageDialog(frame, "Import from file successfully!");
	                updateTable();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
            }
            else if (e.getSource() == jbt_Display_All) {
                updateTable();
            }
            else if (e.getSource() == jbt_Display_by_ISBN) {
                Book [] arr = new Book[book_list.size()];
                book_list.toArray(arr);
                Arrays.sort(arr, sort_by_isbn == 1 ?
                Comparator.comparing(Book:: getISBN).reversed() : Comparator.comparing(Book:: getISBN));
                sort_by_isbn = 1 - sort_by_isbn;
                sort_by_title = 0;
                display(arr);
            }
            else if (e.getSource() == jbt_Display_by_Title) {
                Book [] arr = new Book[book_list.size()];
                book_list.toArray(arr);
                Arrays.sort(arr, sort_by_title == 1 ? 
                Comparator.comparing(Book:: getTitle).reversed() : Comparator.comparing(Book:: getTitle));
                sort_by_title = 1 - sort_by_title;
                sort_by_isbn = 0;
                display(arr);
            }
            else if (e.getSource() == jbt_Exit) {
                System.exit(0);
            }
            else if (e.getSource() == jbt_Borrow) {
                selectedBook.setAvailable(false);
                
                jbt_Borrow.setEnabled(false);
                jbt_Return.setEnabled(true);
                jbt_Reserve.setEnabled(true);
                jbt_Waiting.setEnabled(true);

                info.setText("ISBN: " + selectedBook.getISBN() + "\nTitle: " + selectedBook.getTitle() + "\nAvailable: " + (selectedBook.isAvailable() ? "true" : "false"));
                Msg.setText("The book is borrowed.");
                
                updateTable();
            }
            else if (e.getSource() == jbt_Return) {
                MyQueue<String> Queue = selectedBook.getReservedQueue();
                if(Queue.getSize() == 0) {
                    selectedBook.setAvailable(true);

                    jbt_Borrow.setEnabled(true);
                    jbt_Return.setEnabled(false);
                    jbt_Reserve.setEnabled(false);
                    jbt_Waiting.setEnabled(false);

                    info.setText("ISBN: " + selectedBook.getISBN() + "\nTitle: " + selectedBook.getTitle() + "\nAvailable: " + (selectedBook.isAvailable() ? "true" : "false"));
                    Msg.setText("The book is returned.");
                    
                    updateTable();
                }
                else {
                    String name = Queue.dequeue();
                    Msg.setText("The book is returned.\nThe book is now borrowewd by " + name + ".");
                }
            }
            else if (e.getSource() == jbt_Waiting) {
                String str = new String("The waiting queue:\n");
                for(String name: selectedBook.getReservedQueue().getList())
                    str = str + name + "\n";
                Msg.setText(str);
            }
            else if (e.getSource() == jbt_Reserve) {
                String str = (String)JOptionPane.showInputDialog(frame,
                                "What's your name?",
                                "Input",
                                JOptionPane.QUESTION_MESSAGE);
                selectedBook.getReservedQueue().enqueue(str);
                Msg.setText("The book is reserved by " + str + ".");
            }
        }
    }
    
    private void msg(String str) {
        JOptionPane.showMessageDialog(null, str);
    }

    private void editMode(Boolean state) {
        jbt_Save.setEnabled(state);

        jbt_Add.setEnabled(!state);
        jbt_Edit.setEnabled(!state);
        jbt_Delete.setEnabled(!state);
        jbt_Search.setEnabled(!state);
        jbt_More.setEnabled(!state);
        jbt_Load.setEnabled(!state);
        jbt_Display_All.setEnabled(!state);
        jbt_Display_by_ISBN.setEnabled(!state);
        jbt_Display_by_Title.setEnabled(!state);
        jbt_Exit.setEnabled(!state);
    }

    private int get_by_ISBN(String isbn) {
        int cnt = 0;
        Iterator<Book> it= book_list.iterator();
        while(it.hasNext()){
            Book tmp = it.next();
            if(tmp.getISBN().equals(isbn)) return cnt;
            cnt ++;
        }
        return -1;
    }
    
    private void display(Book[] arr) {
        Object[][] Data = new String[arr.length][3];
        int cnt = 0;
        for(Book item: arr) {
            Data[cnt][0] = item.getISBN();
            Data[cnt][1] = item.getTitle();
            Data[cnt][2] = item.isAvailable() ? "true" : "false";
            cnt ++;
        }
        model = new DefaultTableModel(Data, header) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);
    }

    private void updateTable() {
        Book [] arr = new Book[book_list.size()];
        book_list.toArray(arr);
        sort_by_title = sort_by_isbn = 0;
        display(arr);
    }
    
    private void addBook(Book k){
        if(get_by_ISBN(k.getISBN()) != -1) msg("Error: book ISBN exists in the current database");
        else {
            book_list.addLast(k);
            updateTable();
        }
    }
}
