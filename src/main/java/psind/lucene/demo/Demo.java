package psind.lucene.demo;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import psind.lucene.utility.Constant;

public class Demo {

	private static Analyzer analyzer = new StandardAnalyzer();

	public static void main(String[] args) {
		//indexer();
		search("computer");
	}
	
	private static void search(String searchText) {		
		try {			
			//Searching the Index
			Directory directory = FSDirectory.open(new File(Constant.INDEX_DIRECTORY).toPath());
			DirectoryReader ireader = DirectoryReader.open(directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);		    
			//query
			Query query = new QueryParser("content", analyzer).parse(searchText);		    
			ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				System.out.println(hitDoc.get("content"));
			}
			ireader.close();
			directory.close();
		} catch (Exception e) {
			System.out.println("Exception : "+ e.getMessage());
		}
	}

	private static void indexer() {
			//Indexing
			try {
				System.out.println("indexing started");
				Directory directory = FSDirectory.open(new File(Constant.INDEX_DIRECTORY).toPath());
				IndexWriterConfig config = new IndexWriterConfig(analyzer);
				IndexWriter iwriter = new IndexWriter(directory, config);
				addDocs(iwriter);
				System.out.println("indexing ended");
			} catch (Exception e) {
				System.out.println("Exception : " + e.getMessage());
			}
	}
	
	private static void addDocs(IndexWriter iwriter) throws Exception {
		addDoc(iwriter, "Lucene in Action");
		addDoc(iwriter, "Lucene for Dummies");
		addDoc(iwriter, "Managing in Gigabytes");
		addDoc(iwriter, "The Art of Computer Science");
		iwriter.close();
	}

	private static void addDoc(IndexWriter w, String content) throws IOException {
		  Document doc = new Document();
		  doc.add(new TextField("content", content, Store.YES));
		  w.addDocument(doc);
	}

}
