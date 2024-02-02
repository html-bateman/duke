import edu.duke.*;
import java.io.*;
/**
 * 在这里给出对类 Grayscale 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class Grayscale {
    public ImageResource makeGray(ImageResource inImage){
        ImageResource outImage = new ImageResource(inImage.getWidth(),inImage.getHeight());
        for(Pixel pixel:outImage.pixels()){
            Pixel inPixel = inImage.getPixel(pixel.getX(),pixel.getY());
            int average=(inPixel.getRed()+inPixel.getBlue()+inPixel.getGreen())/3;
            pixel.setRed(average);
            pixel.setGreen(average);
            pixel.setBlue(average);
        }
        return outImage;
    }
    
    public ImageResource makeInversion(ImageResource inImage){
        ImageResource outImage = new ImageResource(inImage.getWidth(),inImage.getHeight());
        for(Pixel pixel:outImage.pixels()){
            Pixel inPixel = inImage.getPixel(pixel.getX(),pixel.getY());
            pixel.setRed(255-inPixel.getRed());
            pixel.setGreen(255-inPixel.getBlue());
            pixel.setBlue(255-inPixel.getGreen());
        }
        return outImage;
    }
    
    public void testGray(){
        ImageResource ir = new ImageResource();
        ImageResource gray = makeGray(ir);
        gray.draw();
    }
    
    public void selectAndConvert(){
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            ImageResource inImage = new ImageResource(f);
            ImageResource gray = makeGray(inImage);
            String fname = inImage.getFileName();
            String newName = "gray-" + fname;
            gray.setFileName(newName);
            gray.save();
        }
    }
    
    public void selectAndInvert(){
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            ImageResource inImage = new ImageResource(f);
            ImageResource gray = makeInversion(inImage);
            String fname = inImage.getFileName();
            String newName = "inverted-" + fname;
            gray.setFileName(newName);
            gray.save();
        }
    }
    
    public void doSave(){
        DirectoryResource dr = new DirectoryResource();
        for(File f:dr.selectedFiles()){
            ImageResource image = new ImageResource(f);
            String fname = image.getFileName();
            String newName = "copy-" + fname;
            image.setFileName(newName);
            image.save();
        }
    }
}
