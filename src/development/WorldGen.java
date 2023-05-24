package development;

import engine.GameObject;

public class WorldGen extends GameObject {

    private Tile[] tiles;
    private float time = 0f;
    private int frontTile = 0;


    @Override
    protected void load() {
        super.load();

        tiles = new Tile[10];
        for (int i = 0; i < 10;i++)
        {
            add(tiles[i] = new Tile());
            tiles[i].setGlobalPosition(i + 1f,5f);
        }
    }

    @Override
    protected void update() {
        super.update();

        if (time >= .4f)
        {
            tiles[frontTile].setGlobalPosition(10f,5f);
            frontTile++;
            if (frontTile > 9)
            {
                frontTile = 0;
            }
            time = 0f;
        }

        time = time + 1f / getFPS();
        System.out.println(time);

    }

}
