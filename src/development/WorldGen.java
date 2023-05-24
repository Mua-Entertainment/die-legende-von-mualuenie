package development;

import engine.GameObject;

public class WorldGen extends GameObject {

    private Chunk[] chunks;
    private float time = 0f;
    private int frontTile = 0;


    @Override
    protected void load() {
        super.load();

        chunks = new Chunk[10];
        for (int i = 0; i < 10;i++)
        {
            add(chunks[i] = new Chunk());
            chunks[i].setGlobalPosition(i + 1f,5f);
        }
    }

    @Override
    protected void update() {
        super.update();

        if (time >= 1.2f)
        {
            chunks[frontTile].setGlobalPosition(10f,5f);
            frontTile++;
            if (frontTile > 9)
            {
                frontTile = 0;
            }
            time = 0f;
        }

        time = time + 1f / getFPS();

    }

}
