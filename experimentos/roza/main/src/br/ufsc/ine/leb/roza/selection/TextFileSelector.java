package src.br.ufsc.ine.leb.roza.selection;

import java.util.List;

import src.br.ufsc.ine.leb.roza.TextFile;

public interface TextFileSelector {

	List<TextFile> select(List<TextFile> files);

}
