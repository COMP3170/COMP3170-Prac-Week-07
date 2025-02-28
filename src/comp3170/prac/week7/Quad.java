package comp3170.prac.week7;


import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL41.*;

import comp3170.GLBuffers;
import comp3170.InputManager;
import comp3170.Shader;
import comp3170.ShaderLibrary;

public class Quad {

	private Vector4f[] vertices;
	private int vertexBuffer;
	private int[] indices;
	private int indexBuffer;
	private Vector2f viewportSize;
	private Vector2i mousePosition = new Vector2i(); // For getting the Vector2i of the mouse position
	private Vector2f mousePositionFloat = new Vector2f(); // For storing the Vector2i as a Vector2f to pass to the fragment shader
	private Shader shader;
	
	final private String VERTEX_SHADER = "vertex.glsl";
	final private String FRAGMENT_SHADER = "fragment.glsl";
	
	public Quad(int width, int height) {
		
		// compile the shader		
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);
		
		viewportSize = new Vector2f(width, height);
		
		//  2 --- 3
		//  | \   |
		//  |  \  |
		//  |   \ |
		//  0 --- 1
		
		vertices = new Vector4f[] {
			new Vector4f(-1,-1,0,1),
			new Vector4f( 1,-1,0,1),
			new Vector4f(-1, 1,0,1),
			new Vector4f( 1, 1,0,1),
		};
		
		vertexBuffer = GLBuffers.createBuffer(vertices);
		
		indices = new int[] {
			0, 1, 2,
			3, 2, 1,
		};
		
		indexBuffer = GLBuffers.createIndexBuffer(indices);
	}
	
	public void resize(int width, int height) {
		viewportSize.x = width;
		viewportSize.y = height;
	}

	public void update(InputManager input, float deltaTime) {
		// get the mouse position in NDC as a vector (x, y, 0, 1)
		input.getCursorPos(mousePosition);
		mousePositionFloat.x = mousePosition.x/(viewportSize.x/2) - 1;
		mousePositionFloat.y = mousePosition.y/(viewportSize.y/2) - 1;
		// NOTE: This won't work, because getCursorPos() returns a y value that treats the top left corner of the screen as (0,0) - how do you fix this?
	}
	
	public void draw() {
		shader.setStrict(false); // Flag to prevent failure when variable isn't being used.
		shader.enable();
		
		shader.setAttribute("a_position", vertexBuffer);
		shader.setUniform("u_viewportSize", viewportSize);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);				
	}

}
