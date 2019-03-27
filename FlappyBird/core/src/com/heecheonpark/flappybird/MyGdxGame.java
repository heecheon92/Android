package com.heecheonpark.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	Texture topTube;
	Texture bottomTube;

	// flapState will generate motion to a bird image.
	int flapState = 0;
	float birdY = 0;
	double velocity = 0;

	int gameState = 0;
	float gravity = 2;
	float gap = 400;
	float maxTubeOffset;
	Random randomGenerator;
	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float distanceBetweenTubes;



	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
		randomGenerator = new Random();
		distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

		for (int i = 0; i < numberOfTubes; i++)
		{

			// Generate tubes to be generated based on gap.
			tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
			tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + i * distanceBetweenTubes;
		}
	}

	/*
	* This is the main function that will work like a loop.
	* */
	@Override
	public void render () {


		batch.begin();
		// Set up background from (0, 0) to full width and height.
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState != 0)
		{
			// Do something when the screen.
			if (Gdx.input.justTouched())
			{
				// Pull the bird up when the screen is touched.
				velocity = -20;
			}

			for (int i = 0; i < numberOfTubes; i++)
			{
				// Remake tubes when they are gone.
				if (tubeX[i] < -topTube.getWidth())
				{
					tubeX[i] += numberOfTubes * distanceBetweenTubes;
				}
				else
				{
					// Move tubes to left.
					tubeX[i] -= tubeVelocity;
				}


				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);
			}
			// Prevent the bird to go lower than the bottom of the screen.
			if (birdY > 0 || velocity < 0)
			{
				// Setting up a gravity effect.
				velocity = velocity + gravity;
				birdY -= velocity;
			}

		}
		else
		{
			if (Gdx.input.justTouched())
			{
				gameState = 1;
			}
		}

		if (flapState == 0)
		{
			flapState = 1;
		}
		else
		{
			flapState = 0;
		}

		// Position the bird at the center.
		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2,  birdY);
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
