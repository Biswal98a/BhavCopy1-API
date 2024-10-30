package com.BhavCopy_1.utill;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.BhavCopy_1.entity.BhavCopy;

public class BhavCopyReader {
	public List<BhavCopy> extractBhavCopyData(String filePath) {

        try {
            String line;
            List<String> lines=new ArrayList<>();
            BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
            int index=0;
            while ((line= bufferedReader.readLine())!=null){
                if (index==0){
                    index++;
                } else if (index>0) {
                    lines.add(line);
                    index++;
                }
            }
            List<BhavCopy> copies=new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                String[] split = lines.get(i).split(",");
                BhavCopy copy=new BhavCopy(split[0].trim(),split[1].trim(),split[2].trim(),Double.parseDouble(split[3].trim()),Double.parseDouble(split[4].trim()),Double.parseDouble(split[5].trim()),
                        Double.parseDouble(split[6].trim()),Double.parseDouble(split[7].trim()),Double.parseDouble(split[8].trim()),Double.parseDouble(split[9].trim()),Double.parseDouble(split[10].trim()),
                        Double.parseDouble(split[11].trim()),Double.parseDouble(split[12].trim()),Double.parseDouble(split[13].trim()),Double.parseDouble(split[14].trim()));
                copies.add(copy);
            }
            return copies;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File is not found in the specified path");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
