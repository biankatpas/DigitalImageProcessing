package br.univali.pdi;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.util.Arrays;

/**
 * @author biankatpas
 */
public class Filtros {

    public Filtros() {
    }

    public BufferedImage abrirImagem(String url) {
        try {
            return ImageIO.read(new File(url));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void pixel(Point p, BufferedImage image) {
        try {
            Robot robot = new Robot();
            Color c = robot.getPixelColor(p.x, p.y);
            JOptionPane.showMessageDialog(null, "\nR: " + c.getRed() + "\nG: " + c.getGreen() + "\nB: " + c.getBlue());
        } catch (AWTException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public BufferedImage cinzaSimples(BufferedImage input) {

        int w = input.getWidth();
        int h = input.getHeight();

        BufferedImage cinza = input;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {

                Color c = new Color(cinza.getRGB(j, i));

                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                Color newColor = new Color((red + green + blue) / 3,
                        (red + green + blue) / 3, (red + green + blue) / 3);

                cinza.setRGB(j, i, newColor.getRGB());
            }
        }

        try {
            ImageIO.write(cinza, "png", new File("src/img/cinzaSimples.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cinza;
    }

    public BufferedImage cinzaPonderado(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();

        BufferedImage cinza = input;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {

                Color c = new Color(input.getRGB(j, i));

                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);

                Color newColor = new Color((red + green + blue) / 3,
                        (red + green + blue) / 3, (red + green + blue) / 3);

                cinza.setRGB(j, i, newColor.getRGB());
            }
        }

        try {
            ImageIO.write(cinza, "png", new File("src/img/cinzaPonderado.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cinza;
    }

    public BufferedImage negativa(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();

        BufferedImage negativa = input;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {

                Color c = new Color(input.getRGB(j, i));

                int red = 255 - c.getRed();
                int green = 255 - c.getGreen();
                int blue = 255 - c.getBlue();

                Color newColor = new Color(red, green, blue);
                negativa.setRGB(j, i, newColor.getRGB());
            }
        }

        try {
            ImageIO.write(negativa, "png", new File("src/img/negativa.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return negativa;
    }

    public BufferedImage limiarizacao(BufferedImage input, int t) {
        int w = input.getWidth();
        int h = input.getHeight();
        int BLACK = Color.BLACK.getRGB();
        int WHITE = Color.WHITE.getRGB();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color pixel = new Color(input.getRGB(j, i));
                input.setRGB(j, i, pixel.getRed() < t ? BLACK : WHITE);
            }
        }

        try {
            ImageIO.write(input, "png", new File("src/img/limiarizacao.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }

        return input;
    }

    private Dimension getTamanhoImagem(BufferedImage img1, BufferedImage img2) {
        Dimension d1 = new Dimension(img1.getWidth(), img1.getHeight());
        Dimension d2 = new Dimension(img2.getWidth(), img2.getHeight());
        Dimension saida = new Dimension();

        if (d1.width > d2.width) {
            saida.width = d2.width;
        } else {
            saida.width = d1.width;
        }

        if (d1.height > d2.height) {
            saida.height = d2.height;
        } else {
            saida.height = d1.height;
        }

        return saida;

    }

    public BufferedImage adicaoSimples(BufferedImage imagem1, BufferedImage imagem2) {
        Dimension tamanho = getTamanhoImagem(imagem1, imagem2);

        for (int i = 0; i < tamanho.height; i++) {
            for (int j = 0; j < tamanho.width; j++) {

                Color px1 = new Color(imagem1.getRGB(j, i));
                Color px2 = new Color(imagem2.getRGB(j, i));

                int r = (px1.getRed() + px2.getRed()) / 2;
                int g = (px1.getGreen() + px2.getGreen()) / 2;
                int b = (px1.getBlue() + px2.getBlue()) / 2;

                Color newColor = new Color(r, g, b);
                imagem1.setRGB(j, i, newColor.getRGB());

            }
        }

        try {
            ImageIO.write(imagem1, "png", new File("src/img/adicaoSimples.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imagem1;
    }

    public BufferedImage adicaoPonderada(BufferedImage imagem1, BufferedImage imagem2, float peso1, float peso2) {
        Dimension tamanho = getTamanhoImagem(imagem1, imagem2);

        for (int i = 0; i < tamanho.height; i++) {
            for (int j = 0; j < tamanho.width; j++) {

                Color px1 = new Color(imagem1.getRGB(j, i));
                Color px2 = new Color(imagem2.getRGB(j, i));

                float r = ((px1.getRed() * (peso1 / 100) + px2.getRed() * (peso2 / 100))) / 2;
                float g = ((px1.getGreen() * (peso1 / 100) + px2.getGreen() * (peso2 / 100))) / 2;
                float b = ((px1.getBlue() * (peso1 / 100) + px2.getBlue() * (peso2 / 100))) / 2;

                if (r > 255) {
                    r = 255;
                }
                if (g > 255) {
                    g = 255;
                }
                if (b > 255) {
                    b = 255;
                }

                Color newColor = new Color((int) r, (int) g, (int) b);
                imagem1.setRGB(j, i, newColor.getRGB());

            }
        }

        try {
            ImageIO.write(imagem1, "png", new File("src/img/adicaoPonderada.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imagem1;
    }

    public BufferedImage subtracao(BufferedImage imagem1, BufferedImage imagem2) {
        Dimension tamanho = getTamanhoImagem(imagem1, imagem2);
        int r, g, b;

        for (int i = 0; i < tamanho.height; i++) {
            for (int j = 0; j < tamanho.width; j++) {

                Color px1 = new Color(imagem1.getRGB(j, i));
                Color px2 = new Color(imagem2.getRGB(j, i));

                if (px1.getRed() > px2.getRed()) {
                    r = (px1.getRed() - px2.getRed());
                } else {
                    r = (px2.getRed() - px1.getRed());
                }

                if (px1.getGreen() > px2.getGreen()) {
                    g = (px1.getGreen() - px2.getGreen());
                } else {
                    g = px2.getGreen() - px1.getGreen();
                }
                if (px1.getBlue() > px2.getBlue()) {
                    b = (px1.getBlue() - px2.getBlue());
                } else {
                    b = px2.getBlue() - px1.getBlue();
                }

                Color newColor = new Color(r, g, b);
                imagem1.setRGB(j, i, newColor.getRGB());
            }
        }

        try {
            ImageIO.write(imagem1, "png", new File("src/img/subtracao.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imagem1;
    }

    public BufferedImage canal(BufferedImage input, String canal) {
        int w = input.getWidth();
        int h = input.getHeight();

        BufferedImage saida = input;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color pixel = new Color(input.getRGB(j, i));

                if (canal.equalsIgnoreCase("red")) {
                    Color newColor = new Color(pixel.getRed(), 0, 0);
                    saida.setRGB(j, i, newColor.getRGB());
                } else if (canal.equalsIgnoreCase("green")) {
                    Color newColor = new Color(0, pixel.getGreen(), 0);
                    saida.setRGB(j, i, newColor.getRGB());
                } else {
                    Color newColor = new Color(0, 0, pixel.getBlue());
                    saida.setRGB(j, i, newColor.getRGB());
                }
            }
        }

        try {
            ImageIO.write(saida, "png", new File("src/img/" + canal + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saida;
    }

    public BufferedImage incrementoPercentual(BufferedImage input, String canal, float perc) {
        int w = input.getWidth();
        int h = input.getHeight();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color pixel = new Color(input.getRGB(j, i));

                if (canal.equalsIgnoreCase("red")) {
                    float r = pixel.getRed() * (1 + perc / 100);
                    if (r > 255) {
                        r = 255;
                    }
                    Color newColor = new Color((int) r, pixel.getGreen(), pixel.getBlue());
                    input.setRGB(j, i, newColor.getRGB());
                } else if (canal.equalsIgnoreCase("green")) {
                    float g = pixel.getGreen() * (1 + perc / 100);
                    if (g > 255) {
                        g = 255;
                    }
                    Color newColor = new Color(pixel.getRed(), (int) g, pixel.getBlue());
                    input.setRGB(j, i, newColor.getRGB());
                } else {
                    float b = pixel.getBlue() * (1 + perc / 100);
                    if (b > 255) {
                        b = 255;
                    }
                    Color newColor = new Color(pixel.getRed(), pixel.getGreen(), (int) b);
                    input.setRGB(j, i, newColor.getRGB());
                }

            }

        }
        try {
            ImageIO.write(input, "png", new File("src/img/" + canal + "IncrementoPerc.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }

    public BufferedImage incrementoAbs(BufferedImage input, String canal, int valor) {
        int w = input.getWidth();
        int h = input.getHeight();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color pixel = new Color(input.getRGB(j, i));

                if (canal.equalsIgnoreCase("red")) {
                    float r = pixel.getRed() + valor;
                    if (r > 255) {
                        r = 255;
                    }
                    Color newColor = new Color((int) r, pixel.getGreen(), pixel.getBlue());
                    input.setRGB(j, i, newColor.getRGB());
                } else if (canal.equalsIgnoreCase("green")) {
                    float g = pixel.getGreen() + valor;
                    if (g > 255) {
                        g = 255;
                    }
                    Color newColor = new Color(pixel.getRed(), (int) g, pixel.getBlue());
                    input.setRGB(j, i, newColor.getRGB());
                } else {
                    float b = pixel.getBlue() + valor;
                    if (b > 255) {
                        b = 255;
                    }
                    Color newColor = new Color(pixel.getRed(), pixel.getGreen(), (int) b);
                    input.setRGB(j, i, newColor.getRGB());
                }

            }

        }
        try {
            ImageIO.write(input, "png", new File("src/img/" + canal + "IncrementoAbs.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }

    public BufferedImage zoomIn(BufferedImage input) {

        int w = input.getWidth();
        int h = input.getHeight();

        BufferedImage zoom = new BufferedImage(w * 2, h * 2, BufferedImage.TYPE_3BYTE_BGR);

        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                for (int k = j * 2; k < (j * 2) + 2; k++) {
                    for (int l = i * 2; l < (i * 2) + 2; l++) {
                        Color pixel = new Color(input.getRGB(i, j));
                        zoom.setRGB(l, k, pixel.getRGB());
                    }
                }
            }
        }

        try {
            ImageIO.write(zoom, "png", new File("src/img/zoomIn.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }

        return zoom;

    }

    public BufferedImage zoomOut(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();
        BufferedImage zoom = new BufferedImage(w / 2, h / 2, BufferedImage.TYPE_3BYTE_BGR);
        int r = 0, g = 0, b = 0;

        for (int j = 0; j < zoom.getHeight(); j++) {
            for (int i = 0; i < zoom.getWidth(); i++) {
                for (int k = (j * 2); k < (j * 2) + 2; k++) {
                    for (int l = (i * 2); l < (i * 2) + 2; l++) {
                        Color pixel = new Color(input.getRGB(l, k));
                        r += pixel.getRed();
                        g += pixel.getGreen();
                        b += pixel.getBlue();
                    }
                }
                Color newColor = new Color((int) r / 4, (int) g / 4, (int) b / 4);
                r = 0;
                g = 0;
                b = 0;
                zoom.setRGB(i, j, newColor.getRGB());
            }
        }
        try {
            ImageIO.write(zoom, "png", new File("src/img/zoomOut.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zoom;
    }

    public BufferedImage dilatacao(BufferedImage imagem, int matriz, int t) {
        //pega uma img limiarizada
        BufferedImage aux = limiarizacao(imagem, t);
        int w = imagem.getWidth();
        int h = imagem.getHeight();
        BufferedImage novaImagem = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        int meioMatriz = (int) matriz / 2;
        // System.out.println(meioMatriz);

        Color newColor = null;
        Color c = null;

        for (int j = 1; j < h - 1; j++) {
            for (int i = 1; i < w - 1; i++) {

                c = new Color(aux.getRGB(i, j));
                //verifica se o ponto eh branco
                if (c.getRed() == 255) {
                    //cria nova cor branca aux
                    newColor = new Color(255, 255, 255);
                    //matriz mascara
                    for (int k = j - meioMatriz; k < j + 1 + meioMatriz; k++) {
                        for (int l = i - meioMatriz; l < i + 1 + meioMatriz; l++) {
                            //validacao das bordas da img
                            if (k < h && k > 0) {
                                if (l < w && l > 0) {
                                    novaImagem.setRGB(l, k, newColor.getRGB());
                                }
                            }
                        }
                    }
                }
            }
        }

        try {
            ImageIO.write(novaImagem, "png", new File("src/img/dilatacao.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return novaImagem;
    }

    public BufferedImage erosao(BufferedImage imagem, int matriz, int t) {

        BufferedImage aux = limiarizacao(imagem, t);
        int w = imagem.getWidth();
        int h = imagem.getHeight();
        BufferedImage novaImagem = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        int meioMatriz = (int) matriz / 2;
        Color c;
        boolean branco = true;
        Color newColor = null;

        for (int j = 1; j < h - 1; j++) {
            for (int i = 1; i < w - 1; i++) {

                branco = true;

                for (int k = j - meioMatriz; k < j + 1 + meioMatriz; k++) {
                    for (int l = i - meioMatriz; l < i + 1 + meioMatriz; l++) {
                        if (k < h && k > 0) {
                            if (l < w && l > 0) {
                                c = new Color(aux.getRGB(l, k));
                                if (c.getRed() == 0) {
                                    branco = false;
                                }
                            }
                        }
                    }
                }

                if (branco == true) {
                    newColor = new Color(255, 255, 255);
                    novaImagem.setRGB(i, j, newColor.getRGB());
                }
            }
        }
        try {
            ImageIO.write(novaImagem, "png", new File("src/img/erosao.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return novaImagem;
    }

    public BufferedImage abertura(BufferedImage imagem, int matriz, int t) {

        int w = imagem.getWidth();
        int h = imagem.getHeight();

        BufferedImage erosao = erosao(imagem, matriz, t);
        BufferedImage dilatacao = dilatacao(erosao, matriz, t);

        try {
            ImageIO.write(dilatacao, "png", new File("src/img/abertura.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dilatacao;

    }

    public BufferedImage fechamento(BufferedImage input, int matriz, int t) {
        int w = input.getWidth();
        int h = input.getHeight();

        BufferedImage dilatacao = dilatacao(input, matriz, t);
        BufferedImage fechamento = erosao(dilatacao, matriz, t);

        try {
            ImageIO.write(fechamento, "png", new File("src/img/fechamento.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechamento;

    }

    public void bgAdaptativo(ArrayList<BufferedImage> frames, BufferedImage saida) {
        //10 frames somados / 10 = mostra na tela (media)
        int w = frames.get(0).getWidth();
        int h = frames.get(0).getHeight();
        int r = 0, g = 0, b = 0;

        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                for (BufferedImage frame : frames) {

                    Color pixel = new Color(frame.getRGB(i, j));
                    r += pixel.getRed() * 0.1;
                    g += pixel.getGreen() * 0.1;
                    b += pixel.getBlue() * 0.1;

                    if (r > 255) {
                        r = 255;
                    }
                    if (g > 255) {
                        g = 255;
                    }
                    if (b > 255) {
                        b = 255;
                    }

                    saida.setRGB(i, j, new Color(r, g, b).getRGB());

                }
                r = 0;
                g = 0;
                b = 0;
            }
        }

        try {
            ImageIO.write(saida, "png", new File("src/img/bgadaptativo.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void bgAlpha(BufferedImage image, BufferedImage saida) {
        //im = (frame atual * 0,1) + im*0,9
        int w = image.getWidth();
        int h = image.getHeight();

        Raster raster = image.getData();
        Raster rasterImg1 = saida.getData();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int[] rgbSaida = new int[3];
                int[] rgb = new int[3];
                int[] rgbNew = new int[3];

                rgb = raster.getPixel(j, i, rgb);
                rgbSaida = rasterImg1.getPixel(j, i, rgbSaida);

                rgbNew[0] = (int) ((int) (rgbSaida[0] * 0.9) + (rgb[0] * 0.1));
                rgbNew[1] = (int) ((int) (rgbSaida[1] * 0.9) + (rgb[1] * 0.1));
                rgbNew[2] = (int) ((int) (rgbSaida[2] * 0.9) + (rgb[2] * 0.1));

                Color newPixel = new Color(rgbNew[0], rgbNew[1], rgbNew[2]);
                saida.setRGB(j, i, newPixel.getRGB());

            }
        }

        try {
            ImageIO.write(saida, "png", new File("src/img/bgalpha.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public BufferedImage convolucao(BufferedImage image) {
        BufferedImage convolucao = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        BufferedImage cinza = cinzaSimples(image);

        int m[][] = new int[3][3];
        int meioMatriz = m.length / 2;

        // inicializa a matriz de convolução
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == meioMatriz && j == meioMatriz) {
                    m[i][j] = 16;
                } else {
                    m[i][j] = -2;
                }
            }
        }

        int mi;
        int mj;

        Raster raster = cinza.getData();
        for (int j = meioMatriz; j < cinza.getHeight() - meioMatriz - 1; j++) {
            for (int i = meioMatriz; i < cinza.getWidth() - meioMatriz - 1; i++) {
                int[] rgb = new int[3];
                int valorPixelFinal = 0;

                mi = 0;
                for (int k = j - meioMatriz; k < j + meioMatriz + 1; k++) {
                    mj = 0;
                    for (int l = i - meioMatriz; l < i + meioMatriz + 1; l++) {
                        rgb = raster.getPixel(l, k, rgb);
                        valorPixelFinal += rgb[0] * m[mi][mj];
                        mj++;
                    }
                    mi++;
                }

                if (valorPixelFinal > 255) {
                    valorPixelFinal = 255;
                }
                if (valorPixelFinal < 0) {
                    valorPixelFinal = 0;
                }

                Color newColor = new Color(valorPixelFinal, valorPixelFinal, valorPixelFinal);
                convolucao.setRGB(i, j, newColor.getRGB());
            }
        }
        try {
            ImageIO.write(convolucao, "png", new File("src/img/convolucao.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return convolucao;
    }

    public BufferedImage sobel(BufferedImage input) {
        int mascaraGy[][] = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
        int mascaraGx[][] = {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
        int w = input.getWidth();
        int h = input.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        Raster raster = cinzaSimples(input).getData();
        int[] arrayPixel = new int[3];

        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {

                int R = raster.getPixel(x, y, arrayPixel)[0];
                int G = raster.getPixel(x, y, arrayPixel)[1];
                int B = raster.getPixel(x, y, arrayPixel)[2];

                int Gy = ((raster.getPixel(x - 1, y - 1, arrayPixel)[0]) * mascaraGy[0][0]) + ((raster.getPixel(x, y - 1, arrayPixel)[0]) * mascaraGy[0][1]) + ((raster.getPixel(x + 1, y - 1, arrayPixel)[0]) * mascaraGy[0][2])
                        + ((raster.getPixel(x - 1, y, arrayPixel)[0]) * mascaraGy[1][0]) + ((raster.getPixel(x, y, arrayPixel)[0]) * mascaraGy[1][1]) + ((raster.getPixel(x + 1, y, arrayPixel)[0]) * mascaraGy[1][2])
                        + ((raster.getPixel(x - 1, y + 1, arrayPixel)[0]) * mascaraGy[2][0]) + ((raster.getPixel(x, y + 1, arrayPixel)[0]) * mascaraGy[2][1]) + ((raster.getPixel(x + 1, y + 1, arrayPixel)[0]) * mascaraGy[2][2]);

                int Gx = ((raster.getPixel(x - 1, y - 1, arrayPixel)[0]) * mascaraGx[0][0]) + ((raster.getPixel(x, y - 1, arrayPixel)[0]) * mascaraGx[0][1]) + ((raster.getPixel(x + 1, y - 1, arrayPixel)[0]) * mascaraGx[0][2])
                        + ((raster.getPixel(x - 1, y, arrayPixel)[0]) * mascaraGx[1][0]) + ((raster.getPixel(x, y, arrayPixel)[0]) * mascaraGx[1][1]) + ((raster.getPixel(x + 1, y, arrayPixel)[0]) * mascaraGx[1][2])
                        + ((raster.getPixel(x - 1, y + 1, arrayPixel)[0]) * mascaraGx[2][0]) + ((raster.getPixel(x, y + 1, arrayPixel)[0]) * mascaraGx[2][1]) + ((raster.getPixel(x + 1, y + 1, arrayPixel)[0]) * mascaraGx[2][2]);

                int gradiente = Math.abs(Gx + Gy);

                if (gradiente > 255) {
                    gradiente = 255;
                }
                if (gradiente < 0) {
                    gradiente = 0;
                }

                Color newColor = new Color(gradiente, gradiente, gradiente);
                output.setRGB(x, y, newColor.getRGB());

            }
        }

        try {
            ImageIO.write(output, "png", new File("src/img/sobel.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }

        return output;
    }

    public BufferedImage roberts(BufferedImage input) {
        int mascaraGx[][] = {{0, 1}, {-1, 0}};
        int mascaraGy[][] = {{1, 0}, {0, -1}};
        int w = input.getWidth();
        int h = input.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        Raster raster = cinzaSimples(input).getData();
        int[] arrayPixel = new int[3];

        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {

                int R = raster.getPixel(x, y, arrayPixel)[0];
                int G = raster.getPixel(x, y, arrayPixel)[1];
                int B = raster.getPixel(x, y, arrayPixel)[2];

                int Gy = ((raster.getPixel(x - 1, y - 1, arrayPixel)[0]) * mascaraGy[0][0]) + ((raster.getPixel(x, y - 1, arrayPixel)[0]) * mascaraGy[0][1])
                        + ((raster.getPixel(x - 1, y, arrayPixel)[0]) * mascaraGy[1][0]) + ((raster.getPixel(x, y, arrayPixel)[0]) * mascaraGy[1][1]);

                int Gx = ((raster.getPixel(x - 1, y - 1, arrayPixel)[0]) * mascaraGx[0][0]) + ((raster.getPixel(x, y - 1, arrayPixel)[0]) * mascaraGx[0][1])
                        + ((raster.getPixel(x - 1, y, arrayPixel)[0]) * mascaraGx[1][0]) + ((raster.getPixel(x, y, arrayPixel)[0]) * mascaraGx[1][1]);

                int gradiente = Math.abs((int) Math.sqrt((Gx * Gx) + (Gy * Gy)));

                if (gradiente > 255) {
                    gradiente = 255;
                }
                if (gradiente < 0) {
                    gradiente = 0;
                }

                Color newColor = new Color(gradiente, gradiente, gradiente);
                output.setRGB(x, y, newColor.getRGB());

            }
        }

        try {
            ImageIO.write(output, "png", new File("src/img/roberts.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }

        return output;
    }

    private Map<Integer, Integer[][]> mascarasRobinsonMap() {
        Map<Integer, Integer[][]> mascaras = new HashMap<>();
        mascaras.put(0, new Integer[][]{{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}});
        mascaras.put(1, new Integer[][]{{2, 1, 0}, {1, 0, -1}, {0, -1, -2}});
        mascaras.put(2, new Integer[][]{{1, 0, -1}, {2, 0, -2}, {1, 0, -1}});
        mascaras.put(3, new Integer[][]{{0, -1, -2}, {1, 0, -1}, {2, 1, 0}});
        mascaras.put(4, new Integer[][]{{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}});
        mascaras.put(5, new Integer[][]{{-2, -1, 0}, {-1, 0, 1}, {0, 1, 2}});
        mascaras.put(6, new Integer[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}});
        mascaras.put(7, new Integer[][]{{0, 1, 2}, {-1, 0, 1}, {-2, -1, 0}});
        return mascaras;
    }

    public BufferedImage robinson(BufferedImage input) {
        int w = input.getWidth(), h = input.getHeight();
        int[] arrayPixel = new int[3], gradientes = new int[8];
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        Raster raster = cinzaSimples(input).getData();

        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {

                int R = raster.getPixel(x, y, arrayPixel)[0];
                int G = raster.getPixel(x, y, arrayPixel)[1];
                int B = raster.getPixel(x, y, arrayPixel)[2];

                for (int k = 0; k < mascarasRobinsonMap().size(); k++) {
                    gradientes[k] = ((raster.getPixel(x - 1, y - 1, arrayPixel)[0]) * mascarasRobinsonMap().get(k)[0][0]) + ((raster.getPixel(x, y - 1, arrayPixel)[0]) * mascarasRobinsonMap().get(k)[0][1]) + ((raster.getPixel(x + 1, y - 1, arrayPixel)[0]) * mascarasRobinsonMap().get(k)[0][2])
                            + ((raster.getPixel(x - 1, y, arrayPixel)[0]) * mascarasRobinsonMap().get(k)[1][0]) + ((raster.getPixel(x, y, arrayPixel)[0]) * mascarasRobinsonMap().get(k)[1][1]) + ((raster.getPixel(x + 1, y, arrayPixel)[0]) * mascarasRobinsonMap().get(k)[1][2])
                            + ((raster.getPixel(x - 1, y + 1, arrayPixel)[0]) * mascarasRobinsonMap().get(k)[2][0]) + ((raster.getPixel(x, y + 1, arrayPixel)[0]) * mascarasRobinsonMap().get(k)[2][1]) + ((raster.getPixel(x + 1, y + 1, arrayPixel)[0]) * mascarasRobinsonMap().get(k)[2][2]);
                }

                Arrays.sort(gradientes);

                if (gradientes[gradientes.length - 1] > 255) {
                    gradientes[gradientes.length - 1] = 255;
                }
                if (gradientes[gradientes.length - 1] < 0) {
                    gradientes[gradientes.length - 1] = 0;
                }

                Color newColor = new Color(gradientes[gradientes.length - 1], gradientes[gradientes.length - 1], gradientes[gradientes.length - 1]);
                output.setRGB(x, y, newColor.getRGB());
            }
        }

        try {
            ImageIO.write(output, "png", new File("src/img/robinson.png"));
        } catch (IOException ex) {
            Logger.getLogger(Filtros.class.getName()).log(Level.SEVERE, null, ex);
        }

        return output;
    }

}
