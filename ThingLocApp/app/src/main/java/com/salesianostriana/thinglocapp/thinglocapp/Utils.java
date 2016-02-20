package com.salesianostriana.thinglocapp.thinglocapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Jesus Pallares on 18/02/2016.
 */
public class Utils {

    public static String putes="";

    /*
       Este método recibe una imagen como un byte[] y devuelve un Bitmap con el tamaño
       adaptado al parámetro IMAGE_BIGGER_SIDE_SIZE.
    */
    public static Bitmap decodeBitmapSize(byte[] byteArray, int IMAGE_BIGGER_SIDE_SIZE){

        //we will return this Bitmap
        Bitmap b = null;


        //We need to know image width and height,
        //for it we create BitmapFactory.Options object  and do BitmapFactory.decodeByteArray
        //inJustDecodeBounds = true - means that we do not need load Bitmap to memory
        //but we need just know width and height of it
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, opt);
        int CurrentWidth = opt.outWidth;
        int CurrentHeight = opt.outHeight;


        //there is function that can quick scale images
        //but we need to give in scale parameter, and scale - it is power of 2
        //for example 0,1,2,4,8,16...
        //what scale we need? for example our image 1000x1000 and we want it will be 100x100
        //we need scale image as match as possible but should leave it more then required size
        //in our case scale=8, we receive image 1000/8 = 125 so  125x125,
        //scale = 16 is incorrect in our case, because we receive 1000/16 = 63 so 63x63 image
        //and it is less then 100X100
        //this block of code calculate scale(we can do it another way, but this way it more clear to read)
        int scale = 1;
        int PowerOf2 = 0;
        int ResW = CurrentWidth;
        int ResH = CurrentHeight;
        if (ResW > IMAGE_BIGGER_SIDE_SIZE || ResH > IMAGE_BIGGER_SIDE_SIZE) {
            while(1==1)
            {
                PowerOf2++;
                scale = (int) Math.pow(2,PowerOf2);
                ResW = (int)((double)opt.outWidth / (double)scale);
                ResH = (int)((double)opt.outHeight / (double)scale);
                if(Math.max(ResW,ResH ) < IMAGE_BIGGER_SIDE_SIZE)
                {
                    PowerOf2--;
                    scale = (int) Math.pow(2,PowerOf2);
                    ResW = (int)((double)opt.outWidth / (double)scale);
                    ResH = (int)((double)opt.outHeight / (double)scale);
                    break;
                }

            }
        }


        //Decode our image using scale that we calculated
        BitmapFactory.Options opt2 = new BitmapFactory.Options();
        opt2.inSampleSize = scale;
        b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, opt2);


        //calculating new width and height
        int w = b.getWidth();
        int h = b.getHeight();
        if(w>=h)
        {
            w = IMAGE_BIGGER_SIDE_SIZE;
            h =(int)( (double)b.getHeight() * ((double)w/b.getWidth()));
        }
        else
        {
            h = IMAGE_BIGGER_SIDE_SIZE;
            w =(int)( (double)b.getWidth() * ((double)h/b.getHeight()));
        }


        //if we lucky and image already has correct sizes after quick scaling - return result
        if(opt2.outHeight==h && opt2.outWidth==w)
        {
            return b;
        }


        //we scaled our image as match as possible using quick method
        //and now we need to scale image to exactly size
        b = Bitmap.createScaledBitmap(b, w,h,true);


        return b;
    }

}
