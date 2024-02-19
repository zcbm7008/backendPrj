package webshop.storage;

import lombok.Getter;

import java.io.File;
import java.nio.file.Path;

@Getter
public class TempFile implements AutoCloseable {
    private File file;

    public TempFile(Path pathname){
        this.file = new File(String.valueOf(pathname));
    }

    @Override
    public void close() {
        if(file != null && file.exists()){
            file.delete();
        }
    }


}
