package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class scraper {


    public ArrayList<flowerEntry> flowers = new ArrayList<>();
    public HashMap<String, String> nameUrl = new HashMap<>();
    public HashMap<String, String> message = new HashMap<>();
    public boolean scrapeFinished = false;







    public static void main(String[] args) throws IOException, InterruptedException {
//        backSpace();
        System.out.println("running");
//        listFilesUsingJavaIO(System.getProperty("user.dir")).forEach(System.out::println);
//
        scraper sc = new scraper();
        sc.init();
        String[] flower_names = {"baby", "tana", "hydrangeas", "pingpong", "calimero", "lisianthus", "rosy"};
        for (String flower_name: flower_names){
            File theDir = new File(System.getProperty("user.dir")+ "\\images\\" + flower_name);
            if (!theDir.exists()){
                theDir.mkdirs();
            }
        }

        sc.test("baby");
        sc.rename();



    }

    public void rename(){
        String[] flower_names = {"baby", "tana", "hydrangeas", "pingpong", "calimero", "lisianthus", "rosy"};
        for (String flower_name: flower_names){
            File folder = new File(System.getProperty("user.dir")+ "\\images\\" + flower_name);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    File f = new File(System.getProperty("user.dir")+"\\images\\"+flower_name +"\\"+listOfFiles[i].getName());
                    f.renameTo(new File(System.getProperty("user.dir")+"\\images\\" +flower_name +"\\"+ flower_name + "_c_" + i +".jpg"));
                }
            }
        }
    }



    public class scrapeTracker extends Thread{
        int charCounter = 0;
        @Override
        public void run(){
            System.out.println("Scraping started");
            while (!scrapeFinished){


                for (Map.Entry<String, String> entry: message.entrySet()){
                    System.out.println(entry.getValue());
                    charCounter += entry.getValue().length();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i = 0 ; i < charCounter; i++){
                    System.out.print("\b");
                }
                charCounter = 0;

            }
            for(int i = 0 ; i < charCounter; i++){
                System.out.print("\b");
            }
            System.out.println("Scraping finished");
        }
    }

    public void init(){

        String[] baby_urls = {"https://dalat.flowers/297/8803_baby-ha-lan-trang-100g.html",
                "https://dalat.flowers/297/8589_baby-ha-lan-hong-100g.html",
                "https://dalat.flowers/297/11820_hoa-baby-da-lat-500g.html",
                "https://dalat.flowers/297/8457_hoa-baby-ha-lan-trang-1000gr.html",
                "https://hoatuoi360.vn/hoa-baby/"};
        String[] tana_urls = {"https://hoatuoi360.vn/cuc-tana/",
                "https://dalat.flowers/86/8425_tana-moc-mac.html",
                "https://dalat.flowers/86/11868_tana-baby.html",
                "https://dalat.flowers/88/6251_binh-hoa-cuc-tana.html",
                "https://dalat.flowers/297/6693_cuc-tana-10.html",
                "https://dalat.flowers/86/8422_lovely-tana.html"};
        String[] hydrangeas_urls = {"https://hoatuoi360.vn/cam-tu-cau/",
                "https://dalat.flowers/297/11819_cam-tu-cau-5-canh.html",
                "https://dalat.flowers/86/6041_bo-hoa-tu-cau.html",
                "https://dalat.flowers/204/5039_hydrangea-hoa-cam-tu-cau.html",
                "https://dalat.flowers/297/6536_cam-tu-cau-xanh-a.html"};
        String[] ping_pong_urls = {"https://dalat.flowers/302/6932_cuc-ping-pong-trang-cty-10.html",
                "https://dalat.flowers/302/6933_hoa-cuc-ping-pong-vang-cty-10.html",
                "https://dalat.flowers/301/6546_cuc-pingpong-tim-10.html",
                "https://dalat.flowers/302/6936_cuc-ping-pong-xanh-cty-10.html",
                "https://dalat.flowers/302/11840_cuc-ping-pong-vang-10-canh.html",
                "https://dalat.flowers/302/6934_cuc-ping-pong-hong-cty-10.html",
                "https://dalat.flowers/221/5893_ping-pong-golden.html"};
        String[] calimero_urls = {"https://dalat.flowers/302/11651_cuc-calimero-trang-5-canh.html",
                "https://dalat.flowers/302/11650_cuc-calimero-vang-nhuy-nau-5-canh.html",
                "https://dalat.flowers/302/11649_cuc-calimero-hong-5-canh.html",
                "https://dalat.flowers/302/11648_cuc-calimero-vang-5-canh.html",
                "https://dalat.flowers/302/11504_cuc-calimero-xanh-5-canh.html",
                "https://dalat.flowers/302/11647_cuc-calimero-tim-5-canh.html"};
        String[] lisianthus_urls = {"https://dalat.flowers/295/6535_cat-tuong-xanh-500g.html",
                "https://dalat.flowers/295/6529_cat-tuong-trang-500g.html",
                "https://dalat.flowers/295/6527_cat-tuong-tim-vien-500g.html",
                "https://dalat.flowers/295/6523_cat-tuong-hong-vien-500g.html"};
        String[] rossi_urls = {"https://dalat.flowers/302/7904_cuc-rossi-cam-orange-cty-10.html",
                "https://dalat.flowers/302/11666_cuc-rossi-vang-5-canh.html",
                "https://dalat.flowers/302/11654_cuc-rossi-trang-5-canh.html",
                "https://dalat.flowers/302/11654_cuc-rossi-trang-5-canh.html",
                "https://dalat.flowers/302/11654_cuc-rossi-trang-5-canh.html",
                "https://dalat.flowers/302/11653_cuc-rossi-tim-5-canh.html",
                "https://dalat.flowers/302/11652_cuc-rossi-hong-5-canh.html"};

        ArrayList<String[]> urls = new ArrayList<>();
        urls.add(baby_urls);
        urls.add(tana_urls);
        urls.add(hydrangeas_urls);
        urls.add(ping_pong_urls);
        urls.add(calimero_urls);
        urls.add(lisianthus_urls);
        urls.add(rossi_urls);
        String[] flower_names = {"baby", "tana", "hydrangeas", "ping_pong", "calimero", "lisianthus", "rossi"};

        ArrayList<MultiSiteClassifier> msc = new ArrayList<>();
        int i = 0;
        for(String[] entry: urls){
            msc.add(new MultiSiteClassifier(entry, flower_names[i]));
            msc.get(i).start();
            i++;
        }
        boolean stopWait = false;
        while (!stopWait){
            int ready_count = 0;
            for(MultiSiteClassifier entry: msc){
                if(entry.finish){
                    ready_count++;
                }
            }
            stopWait = (ready_count == i);
        }
        for(MultiSiteClassifier entry: msc){
            flowers.add(new flowerEntry(entry.flower, entry.uc));

        }


    }

    public void test(String flower) throws IOException, InterruptedException{
        ArrayList<MultiScrape> ms = new ArrayList<>();
        ArrayList<MultiDownload> md = new ArrayList<>();
        int max_count = 0;
        int i = 0;
        scrapeTracker st = new scrapeTracker();
        st.start();
        int counter = 0;
        int j = 0;
//        flowerEntry entry = new flowerEntry();
//        for (flowerEntry name: flowers){
//            if(flower == name.flowerName){
//                 entry = flowers.get(counter);
//            }
//            counter++;
//        }
//
//
//
//        for (urlContainer container : entry.flowerUrl) {
//            for (String url : container.url) {
//                ms.add(new MultiScrape(url, container.site));
//                ms.get(i).start();
//                i++;
//            }
//        }
//        boolean stop = false;
//        ArrayList<MultiScrape> finished = new ArrayList<>();
//        while(!stop){
//            int ready_count = 0;
//            for(MultiScrape entry2: ms){
//                if(entry2.finish && !finished.contains(entry2)){
//                    finished.add(entry2);
//                }
//            }
//            stop = (finished.size() == ms.size());
//        }
//        scrapeFinished = true;
//        System.out.println("done scraping for: " + entry.flowerName);
//        int j = 0;
//        for (MultiScrape entry2: ms){
//            for (String link : entry2.imgUrls) {
//                md.add(new MultiDownload(link,System.getProperty("user.dir")+"\\src\\main\\java\\main\\images\\"+entry.flowerName));
//                md.get(j).start();
//                j++;
//            }
//            max_count += entry2.count;
//
//        }
//        boolean stop2 = false;
//        ArrayList<MultiDownload> finishList = new ArrayList<>();
//        while(!stop2){
//            for(MultiDownload entry2: md){
//                if(entry2.finish && !finishList.contains(entry2)){
//                    finishList.add(entry2);
//                    System.out.println("finished: " + entry2.img);
//                }
//            }
//            stop2 = (finishList.size() == md.size());
//        }

        for (flowerEntry entry: flowers){
            for (urlContainer container : entry.flowerUrl) {
                for (String url : container.url) {
                    ms.add(new MultiScrape(url, container.site));
                    ms.get(i).start();
                    i++;
                }
            }
            boolean stop = false;
            ArrayList<MultiScrape> finished = new ArrayList<>();
            while(!stop){
                int ready_count = 0;
                for(MultiScrape entry2: ms){
                    if(entry2.finish && !finished.contains(entry2)){
                        finished.add(entry2);
                    }
                }
                stop = (finished.size() == ms.size());
            }
            scrapeFinished = true;
//            System.out.println("done scraping for: " + entry.flowerName);

            for (MultiScrape entry2: ms){
                for (String link : entry2.imgUrls) {
                    System.out.println(entry.flowerName);
                    md.add(new MultiDownload(link,System.getProperty("user.dir")+"\\images\\" + entry.flowerName+"\\"));
                    md.get(j).start();
                    j++;
                }
                max_count += entry2.count;

            }
            boolean stop2 = false;
            ArrayList<MultiDownload> finishList = new ArrayList<>();
            while(!stop2){
                for(MultiDownload entry2: md){
                    if(entry2.finish && !finishList.contains(entry2)){
                        finishList.add(entry2);
//                        System.out.println("finished: " + entry2.img);
                    }
                }
                stop2 = (finishList.size() == md.size());
            }

        }


    }



//    public void getAll() throws IOException, InterruptedException {
//        boolean stopWait = false;
//        ArrayList<MultiScrape> ms = new ArrayList<>();
//        ArrayList<MultiDownload> md = new ArrayList<>();
//        int max_count = 0;
//
//        int i = 0;
//
////        for (flowerEntry url: flowers){
////            ms.add(new MultiScrape(url));
////            ms.get(i).start();
////            i++;
////        }
//        while(!stopWait){
//            int ready_count = 0;
//            for(MultiScrape entry: ms){
//                if(entry.finish){
//                    ready_count++;
//                }
//            }
//            stopWait = (ready_count == i);
//        }
//        System.out.println("ms size: " + i);
//        for (MultiScrape entry: ms){
//            for (String link : entry.imgUrls) {
//                md.add(new MultiDownload(link));
//            }
//            max_count += entry.count;
//        }
//        System.out.println("max count: " + max_count);
//        System.out.println("md size: " + md.size());
//        System.out.println("line 67");
//        for (MultiDownload entry: md){
//            entry.start();
//        }
//
//
//    }



    public static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }





    public class MultiSiteClassifier extends Thread{
        String flower;
        ArrayList<String> urls = new ArrayList<>();
        urlContainer[] uc = {
                new urlContainer(new ArrayList<String>(), "dalat.flowers"),
                new urlContainer(new ArrayList<String>(), "hoatuoi360.vn")
        };
        boolean finish = false;
        public MultiSiteClassifier(String[] urls, String flower){
            Collections.addAll(this.urls, urls);
            this.flower = flower;
        }
        @Override
        public void run() {
            message.put(this.getName(),"running thread:" + this.getName() + "type: MultiSiteClassifier");
            ArrayList<SiteClassifierWorker> scw = new ArrayList<>();
            int i = 0;
            for(String url: urls){
                scw.add(new SiteClassifierWorker(url));
                scw.get(i).start();
                i++;
            }
            boolean stopWait = false;
            while(!stopWait){
                int ready_count = 0;
                for(SiteClassifierWorker entry: scw){
                    if(entry.finish){
                        ready_count++;
                    }
                }
                stopWait = (ready_count == i);
            }
            for (SiteClassifierWorker entry: scw){
                if(entry.site.equals("dalat.flowers")){
                    uc[0].url.add(entry.url);
                }
                else{
                    uc[1].url.add(entry.url);
                }
            }
            finish = true;
            message.remove(this.getName());
        }
        public String toString(){
            String output = "";
            for(urlContainer entry: uc){
                output += entry.site + "\n";
                for(String url: entry.url){
                    output += url + "\n";
                }
            }
            return output;
        }
    }

    public class SiteClassifierWorker extends Thread{
        String url;
        String site = "";
        boolean finish = false;
        public SiteClassifierWorker(String url){
            this.url = url;
        }
        @Override
        public void run() {
            message.put(this.getName(),"running thread:" + this.getName() + "type: SiteClassifierWorker");
            if (url.substring(url.indexOf("https://"), 8 +  url.split("https://")[1].indexOf("/")).contains("dalat.flowers")){
                site = "dalat.flowers";
            }
            else {
                site = "hoatuoi360.vn";
            }
            finish = true;
            message.remove(this.getName());
        }
    }

    public class MultiScrape extends Thread{
        public int retryCount = 0;
        public boolean finish = false;
        public int count = 0;
        public String site;
        public String url;
        public ArrayList<String> imgUrls = new ArrayList<>();
        public MultiScrape(String url, String site){
            this.url = url;
            this.site = site;
        }
        @Override
        public void run() {
            message.put(this.getName(),"running thread:" + this.getName() + "type: MultiScrape");
            int pages = 1;
            try {
                Document doc = Jsoup.connect(url).get();
                if(site.equals("dalat.flowers")){
                    for(Element div: doc.getElementsByClass("thumb-image")){
                        String img = div.getElementsByTag("img").attr("src");
                        nameUrl.put(img.substring(img.lastIndexOf("/")+1), img);
                        imgUrls.add("https://dalat.flowers" + img);
                        count++;
                    }
                }
                else {
                    for(Element p: doc.getElementsByClass("page-nav")){
                        for (Element a: p.getElementsByTag("a")){
                            if(a.hasText()){
                                if(isNumeric(a.text())){
                                    if(Integer.parseInt(a.text()) > 1){
                                        if (pages < Integer.parseInt(a.text())){
                                            pages = Integer.parseInt(a.text());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    ArrayList<MultiScrapeHoaTuoi360Pages > ms = new ArrayList<>();
                    for (int index = 1; index < pages; index++){
                        ms.add(new MultiScrapeHoaTuoi360Pages(url.substring(0,url.length()-1)+"?page=" + index));
                        ms.get(index-1).start();
                    }
                    boolean stopWait = false;
                    ArrayList<MultiScrapeHoaTuoi360Pages> finished = new ArrayList<>();
                    while(!stopWait){
                        for(MultiScrapeHoaTuoi360Pages entry: ms){
                            if(entry.finish && !finished.contains(entry)){
                                finished.add(entry);
                            }
                        }
                        stopWait = (finished.size() == ms.size());
                    }
                    for(MultiScrapeHoaTuoi360Pages entry: ms){
                        for(String result: entry.imgUrls){
                            imgUrls.add(result);
                            count++;
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();

                retry();
            }
            this.finish = true;
            message.remove(this.getName());
        }

        public void retry(){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            retryCount++;
            if (retryCount < 5) {
                run();
            }
            else {
                finish = true;
                message.remove(this.getName());
            }
        }
    }

     public class MultiScrapeHoaTuoi360Pages extends Thread{
        boolean finish = false;
        String url;
        ArrayList<String> results = new ArrayList<>();
        ArrayList<String> imgUrls = new ArrayList<>();
        public MultiScrapeHoaTuoi360Pages(String url){
            this.url = url;
        }
        @Override
        public void run() {
            try {
                message.put(this.getName(),"running thread:" + this.getName() + "type: MultiScrapeHoaTuoi360Pages");

                Document doc = Jsoup.connect(url).get();

                for(Element ul: doc.select("ul#include_load_pages")){

                    for(Element li: ul.getElementsByTag("li")){
                        results.add(li.getElementsByTag("a").attr("href"));
                        for(Element a: li.getElementsByTag("a")) {
                            for (Element img : li.getElementsByTag("img")) {
                                imgUrls.add(img.attr("src"));
                            }
                        }
                    }
                }

//                for (String result : results){
//                    Document doc2 = Jsoup.connect("https://hoatuoi360.vn" + result).get();
//                    System.out.println("getting hoa");
//                    System.out.println(result);
//                    if (doc.toString().contains("Cannot connect to Database")) {
//                        System.out.println("timeout");
//                        throw new SocketTimeoutException("cannot connect to database");
//                    }
//                    for(Element div: doc.getElementsByClass("m-i2-cpd")){
//                        System.out.println("div");
//                        for(Element img: div.getElementsByTag("img")){
//                            imgUrls.add("https://hotuoi360.vn/" + img.attr("src").substring(3));
//                        }
//                    }
//                }


//                ArrayList<MultiScrapeHoaTuoi360Indie> ms = new ArrayList<>();
//                int i = 0;
//                int prevIteration = 0;
//                for(String result: results){
//                    ms.add(new MultiScrapeHoaTuoi360Indie("https://hoatuoi360.vn" + result));
//                    ms.get(i).start();
//                    i++;
//                    boolean iterationWait = false;
//                    if (i - prevIteration > 10){
//                        while(!iterationWait){
//                            int ready_count = 0;
//                            for(int entry = prevIteration; entry < i; entry++){
//                                if(ms.get(entry).finish){
//                                    ready_count++;
//                                }
//                            }
//                            iterationWait = (ready_count == i);
//
//                        }
//                        prevIteration = i;
//                    }
//                    int countIndex = 0;
//                    while (countIndex < ms.size()){
//                        for (MultiScrapeHoaTuoi360Indie entry: ms){
//                            if(entry.imgUrl.isEmpty()){
//                                ms.remove(countIndex);
//                                i--;
//                                break;
//                            }
//                            countIndex++;
//                        }
//                        if (countIndex < ms.size()){
//                            countIndex = 0;
//                        }
//                    }
//                }
//                boolean stopWait = false;
//                ArrayList<MultiScrapeHoaTuoi360Indie> finished = new ArrayList<>();
//                while(!stopWait){
//                    for(MultiScrapeHoaTuoi360Indie entry: ms){
//                        if(entry.finish && !finished.contains(entry)){
//                            finished.add(entry);
//                        }
//                    }
//                    stopWait = (finished.size() == ms.size());
//                }
//                for(MultiScrapeHoaTuoi360Indie entry: ms){
//                    imgUrls.add(entry.imgUrl);
//                }

                finish = true;
                System.out.println(imgUrls.toString());
                message.remove(this.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

//    public class MultiScrapeHoaTuoi360Indie extends Thread{
//        int tryCount = 0;
//        boolean finish = false;
//        String imgUrl = "";
//        String url;
//        public MultiScrapeHoaTuoi360Indie(String url){
//            this.url = url;
//        }
//        @Override
//        public void run() {
//            try {
//                message.put(this.getName(),"running thread:" + this.getName() + "type: MultiScrapeHoaTuoi360Indie");
//                Document doc = Jsoup.connect(url).get();
//                System.out.println("getting hoa");
//                if (doc.toString().contains("Cannot connect to Database")) {
//                    throw new SocketTimeoutException("cannot connect to database");
//                }
//                for(Element div: doc.getElementsByClass("m-i2-cpd")){
//                    System.out.println("div");
//                    for(Element img: div.getElementsByTag("img")){
//                        imgUrl = "https://hotuoi360.vn/" + img.attr("src").substring(3);
//                    }
//                }
//                finish = true;
//                System.out.println("hoa: " + imgUrl);
//                message.remove(this.getName());
//            } catch (SocketTimeoutException e) {
//                retry();
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        public void retry(){
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            tryCount++;
//            if(tryCount < 5){
//                this.run();
//            }
//            else {
//                finish = true;
//                message.remove(this.getName());
//            }
//        }
//    }



    public class MultiDownload extends Thread{
        public boolean finish = false;
        public String img;
        public String path;
        public MultiDownload(String img, String path){
            this.img = img;
            this.path = path;
        }
        @Override
        public void run() {
            try{
//                System.out.println("downoading: " + img);
                System.out.println("path: " + path + img.substring(img.lastIndexOf('/')+1));
                URL imgUrl = new URL(img);
                Image image = ImageIO.read(imgUrl);
                FileOutputStream fos = new FileOutputStream(path + img.substring(img.lastIndexOf('/')+1));
                InputStream in = new BufferedInputStream(imgUrl.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1!=(n=in.read(buf)))
                {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();
                fos.write(response);
                fos.close();
                finish = true;
            } catch (IOException e) {
                // handle IOException
                System.out.println("error");
                System.out.println(this.getName());
            }
        }
    }

    public class urlContainer {
        public ArrayList<String> url = new ArrayList<>();
        public String site;
        public urlContainer(ArrayList<String> url, String site){
            this.url = url;
            this.site = site;
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public class flowerEntry{
        public String flowerName;
        public urlContainer[] flowerUrl;
        public flowerEntry(String flowerName, urlContainer[] flowerUrl) {
            this.flowerName = flowerName;
            this.flowerUrl = flowerUrl;
        }

        public flowerEntry() {
            this.flowerName = "";
        }
    }
}


