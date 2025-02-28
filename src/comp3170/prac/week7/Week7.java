	package comp3170.prac.week7;


import static org.lwjgl.opengl.GL41.*;

import java.io.File;
import java.io.IOException;


import comp3170.IWindowListener;
import comp3170.Window;
import comp3170.OpenGLException;
import comp3170.InputManager;
import comp3170.ShaderLibrary;

public class Week7 implements IWindowListener {

	private Window window;
	private int width = 800;
	private int height = 800;
	
	final private File DIRECTORY = new File("src/comp3170/prac/week7"); 
	
	private long oldTime;
	private InputManager input;

	private Quad quad;

	public Week7() throws OpenGLException {

		window = new Window("Week 7 prac", width, height, this);
		window.setResizable(true);
		window.run();
	}

	@Override
	public void init() {
		input = new InputManager(window);
		
		new ShaderLibrary(DIRECTORY);
		
		quad = new Quad(width, height);
		
		oldTime = System.currentTimeMillis();
		
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);	
	}

	private void update() {
		long time = System.currentTimeMillis();
		float deltaTime = (time-oldTime) / 1000f;
		oldTime = time;
		
		quad.update(input, deltaTime);
		
		// clear input at the end of each frame
		input.clear();
	}

	@Override
	public void draw() {
		update();
	
		glClear(GL_COLOR_BUFFER_BIT);		

		quad.draw();		
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;		
		quad.resize(width, height);
		glViewport(0,0,width,height);
	}

	@Override
	public void close() {
		
	}
	
	public static void main(String[] args)  throws IOException, OpenGLException { 
		new Week7();
	}

}
